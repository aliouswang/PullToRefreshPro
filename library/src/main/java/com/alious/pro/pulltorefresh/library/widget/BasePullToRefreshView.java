package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.alious.pro.pulltorefresh.library.R;
import com.alious.pro.pulltorefresh.library.Utils;

/**
 * Base pull to refresh view
 *
 * Created by aliouswang on 16/9/12.
 */
public abstract class BasePullToRefreshView extends FrameLayout{

    public static final String TAG = "pull_pro";

    public static final int STATE_NONE = 0x00;
    public static final int STATE_PULL_DOWN = 0x01;
    public static final int STATE_LOOSE_REFRESH = 0x02;
    public static final int STATE_REFRESHING = 0x03;
    public static final int STATE_RESTORE = 0x04;

    private static final float DEFAULT_PULL_FACTOR = 0.6f;
    private static final int DEFAULT_ANIM_DURATION = 1200;

    //dip
    private static final int DEFAULT_PULL_DISTANCE = 120;

    private volatile int mCurrentState = STATE_NONE;

    private View mPullLoadingView;
    private Scroller mScroller;

    private int mScrollDuration = DEFAULT_ANIM_DURATION;
    private float mPullFactor = DEFAULT_PULL_FACTOR;
    private float mDefaultPullDistance;

    public BasePullToRefreshView(Context context) {
        this(context, null);
    }

    public BasePullToRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasePullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mDefaultPullDistance = Utils.dipToPx(getContext(), DEFAULT_PULL_DISTANCE);
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGray));
        mScroller = new Scroller(getContext());
        mPullLoadingView = LayoutInflater.from(getContext())
                .inflate(getInflateLayout(), this, false);
        this.addView(mPullLoadingView);
    }

    protected abstract int getInflateLayout();

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
                scrollBy(0, (int) ((mLastScrollY - curY) * mPullFactor));
                mLastScrollY = curY;
                float percent = Math.abs((float)getScrollY() / mDefaultPullDistance);
                if (percent >= 1.0f) {
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
                onPullYPercent(Math.min(percent, 1.0f));
                break;
            case MotionEvent.ACTION_UP:
                mLastScrollY = 0f;
                mScroller.startScroll(getScrollX(), getScrollY(), 0, -getScrollY(), mScrollDuration);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    protected void onPullYPercent(float yDistance) {

    }

    /**
     * on pull down, prepare for refresh
     */
    protected void onPullDown() {
        Log.e(TAG, "onPullDown");
    }

    /**
     * on pull finished, loose to refresh
     */
    protected void onLooseRefresh() {
        Log.e(TAG, "onLooseRefresh");
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mPullLoadingView.layout(l, -mPullLoadingView.getMeasuredHeight(), r, 0);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
