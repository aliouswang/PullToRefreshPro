package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.TextView;

import com.alious.pro.pulltorefresh.library.R;
import com.alious.pro.pulltorefresh.library.Utils;
import com.alious.pro.pulltorefresh.library.interfaces.IPercentView;
import com.alious.pro.pulltorefresh.library.listener.OnRefreshListener;

/**
 * Base pull to refresh view
 *
 * Created by aliouswang on 16/9/12.
 */
public class ProPullToRefreshView extends FrameLayout{

    public static final String TAG = "pull_pro";

    private static final int INVALID_POINTER = -1;
    private static final int DEFAULT_MAX_PULL_DISTANCE = 2000;
    private static final int DEFAULT_REFRESHING_HEIGHT = 200;

    public static final String PULL_TO_REFRESH = "下拉刷新";
    public static final String LOOSE_TO_REFRESH = "松开刷新";
    public static final String REFRESHING = "加载中...";

    public static final int STATE_NONE = 0x00;
    public static final int STATE_PULL_DOWN = 0x01;
    public static final int STATE_LOOSE_REFRESH = 0x02;
    public static final int STATE_REFRESHING = 0x03;
    public static final int STATE_RESTORE = 0x04;
    public static final int STATE_PRE_REFRESHING_RESTORE = 0x05;

    public final String SCROLL_DEFAULT_STYLE;
    public final String SCROLL_PARALLEL_STYLE;


    private Context mContext;
    private String mScrollStyle;
    private String mPullRefreshStyle;
    private int mLoadingLayoutId;
    private int mMaxPullDistance;
    private int mRefreshingBarHeight;

    private static final float DEFAULT_PULL_FACTOR = 0.6f;
    private static final int DEFAULT_ANIM_DURATION = 1000;
    private static final int DEFAULT_REFRESH_RESTORE_DURATION = 400;

    //dip
    private static final int DEFAULT_PULL_DISTANCE = 80;

    private volatile int mCurrentState = STATE_NONE;

    private OnRefreshListener mOnRefreshListener;

    protected View mPullLoadingView;
    private OverScroller mScroller;

    private int mScrollDuration = DEFAULT_ANIM_DURATION;
    private float mPullFactor = DEFAULT_PULL_FACTOR;
    private float mPullDistance;

    private int mTouchSlop;
    private float mInitialMotionY;
    private float mInitialDownY;
    private boolean mIsBeingDragged;
    private int mActivePointerId = INVALID_POINTER;
    private float mPullPercent;

    private View mTarget;
    private IPercentView mIPercentView;
    private TextView tv_loading;

    public ProPullToRefreshView(Context context) {
        this(context, null);
    }

    public ProPullToRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProPullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        SCROLL_DEFAULT_STYLE = mContext.getString(R.string.pull_scroll_default_style);
        SCROLL_PARALLEL_STYLE = mContext.getString(R.string.pull_scroll_parallel_style);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.PullRefreshView, 0, 0);
        mScrollStyle = typedArray.getString(R.styleable.PullRefreshView_scrollStyle);
        mPullRefreshStyle = typedArray.getString(R.styleable.PullRefreshView_refreshStyle);
        mLoadingLayoutId = typedArray.getResourceId(R.styleable.PullRefreshView_loadingLayout, R.layout.layout_default_pull_loading);
        mMaxPullDistance = typedArray.getDimensionPixelSize(R.styleable.PullRefreshView_maxPullDistance, DEFAULT_MAX_PULL_DISTANCE);
        mRefreshingBarHeight = typedArray.getDimensionPixelSize(R.styleable.PullRefreshView_refreshingHeight,
                DEFAULT_REFRESHING_HEIGHT);
        mPullDistance = typedArray.getDimensionPixelSize(R.styleable.PullRefreshView_pullDistance,
                Utils.dipToPx(mContext, DEFAULT_PULL_DISTANCE));
        typedArray.recycle();
        if (TextUtils.isEmpty(mScrollStyle)) {
            mScrollStyle = context.getString(R.string.pull_scroll_default_style);
        }
        if (TextUtils.isEmpty(mPullRefreshStyle)) {
            mPullRefreshStyle = context.getString(R.string.pull_refresh_default_style);
        }
        initView();
    }

    protected void initView() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGray));
        mScroller = new OverScroller(getContext());
        mPullLoadingView = LayoutInflater.from(getContext())
                .inflate(mLoadingLayoutId, this, false);
        mPullLoadingView.setId(R.id.pull_refresh_loading_id);
        this.addView(mPullLoadingView);
        mIPercentView = (IPercentView) mPullLoadingView.findViewById(R.id.loading_view);
        tv_loading = (TextView) mPullLoadingView.findViewById(R.id.tv_loading);

        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                tv_loading.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setRefresh(false);
                    }
                }, 8000);
            }
        });
    }

    public OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();
        final int action = MotionEventCompat.getActionMasked(ev);
        int pointerIndex;
        if (canChildScrollUp()) {
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                mIsBeingDragged = false;
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                mInitialDownY = ev.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                final float y = ev.getY(pointerIndex);
                startDragging(y);
                break;
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                break;
        }
        return mIsBeingDragged;
    }

    private void startDragging(float y) {
        final float yDiff = y - mInitialDownY;
        if (yDiff > mTouchSlop && !mIsBeingDragged) {
            mInitialMotionY = mInitialDownY + mTouchSlop;
            mIsBeingDragged = true;
        }
    }

    private void ensureTarget() {
        if (mTarget == null) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = getChildAt(i);
                if (view.getId() != R.id.pull_refresh_loading_id) {
                    mTarget = view;
                    return;
                }
            }
        }
    }

    private boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    private float mLastScrollY = 0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mScroller.isFinished()) {
//            mScroller.abortAnimation();
            return false;
        }
        int action = event.getAction();
        float curY = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mCurrentState = STATE_PULL_DOWN;
                return true;
            case MotionEvent.ACTION_MOVE:
                if (mLastScrollY == 0f) {
                    mLastScrollY = curY;
                }
                if (Math.abs(getScrollY()) > mMaxPullDistance) {
                    break;
                }
                scrollBy(0, (int) ((mLastScrollY - curY) * mPullFactor));
                mLastScrollY = curY;
                float percent = Math.abs((float)getScrollY() / mPullDistance);
                if (percent >= 1.0f && getScrollY() < 0) {
                    if (mCurrentState != STATE_LOOSE_REFRESH) {
                        onLooseRefresh();
                    }
                    mCurrentState = STATE_LOOSE_REFRESH;
                }else {
                    if (mCurrentState != STATE_PULL_DOWN) {
                        onPullDown();
                    }
                    mCurrentState = STATE_PULL_DOWN;
                }
                mPullPercent = Math.min(percent, 1.0f);
                onPullYPercent(mPullPercent);
                break;
            case MotionEvent.ACTION_UP:
                mLastScrollY = 0f;
                if (mCurrentState == STATE_LOOSE_REFRESH) {
                    scrollToRefresh();
                }else {
                    scrollToRestore();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void scrollToRefresh() {
        mCurrentState = STATE_PRE_REFRESHING_RESTORE;
        mScroller.startScroll(0,
                getScrollY(),
                0,
                -getScrollY() - mRefreshingBarHeight,
                DEFAULT_REFRESH_RESTORE_DURATION);
        invalidate();
    }

    private void scrollToRestore() {
        mCurrentState = STATE_RESTORE;
        mScroller.startScroll(0,
                getScrollY(),
                0,
                -getScrollY(),
                mScrollDuration);
        invalidate();
    }

    protected void onRestore() {
        mIPercentView.setRefreshing(false);
        tv_loading.setText(ProPullToRefreshView.PULL_TO_REFRESH);
    }

    protected void onPullYPercent(float yDistance) {
        mIPercentView.setPercent(yDistance);
    }

    /**
     * on pull down, prepare for refresh
     */
    protected void onPullDown() {
        tv_loading.setText(ProPullToRefreshView.PULL_TO_REFRESH);
        mIPercentView.setRefreshing(false);
    }

    /**
     * on pull finished, loose to refresh
     */
    protected void onLooseRefresh() {
        tv_loading.setText(ProPullToRefreshView.LOOSE_TO_REFRESH);
        mIPercentView.onLooseRefresh();
    }
//
//    protected void onPreRefreshRestore(float yDistance) {
//        onPullYPercent(yDistance);
//    }

    /**
     * onRefresh ing.
     */
    protected void onRefreshing() {
        tv_loading.setText(ProPullToRefreshView.REFRESHING);
        mIPercentView.setRefreshing(true);
    }

    /**
     * start refresh
     */
    protected void setRefresh(boolean bRefresh) {
        if (bRefresh) {

        }else {
            scrollToRestore();
        }
    }

//    /**
//     * get refresh bar height
//     *
//     * @return
//     */
//    protected int getRefreshBarHeight() {
//        return 60;
//    }

//    protected int getPullRefreshDistance() {
//        return DEFAULT_PULL_DISTANCE;
//    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (SCROLL_DEFAULT_STYLE.equals(mScrollStyle)) {
            mPullLoadingView.layout(l, -mPullLoadingView.getMeasuredHeight(), r, 0);
        }else if (SCROLL_PARALLEL_STYLE.equals(mScrollStyle)) {
            mPullLoadingView.layout(l, (int) (-mPullLoadingView.getMeasuredHeight() + Utils.dipToPx(mContext, 40)
                                - mPullPercent * Utils.dipToPx(mContext, 40)), r, 0);
        }

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            float percent = Math.abs((float)getScrollY() / mPullDistance);
            mPullPercent = Math.min(percent, 1.0f);
            mIPercentView.setPercent(mPullPercent);
            invalidate();
        }else {
            if (mCurrentState == STATE_PRE_REFRESHING_RESTORE) {
                mCurrentState = STATE_REFRESHING;
                onRefreshing();
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onRefresh();
                }
                invalidate();
            }else if (mCurrentState == STATE_RESTORE) {
                onRestore();
                mPullPercent = 0;
            }
            else if (mCurrentState == STATE_PULL_DOWN) {
                onRestore();
                mPullPercent = 0;
            }
        }
    }
}
