package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.alious.pro.pulltorefresh.library.R;
import com.alious.pro.pulltorefresh.library.interfaces.IPercentView;

/**
 * MeiTuan loading View
 *
 * Created by aliouswang on 16/9/19.
 */
public class MeiTuanLoadingView extends View implements IPercentView{

    private static final int STATE_PULL = 0x01;
    private static final int STATE_PULL_ANIM = 0x02;
    private static final int STATE_REFRESHING = 0x03;

    private int mState = STATE_PULL;

    private Context mContext;
    private Paint mBitmapPaint;

    private RectF mDrawBounds;
    private RectF mPullDrawBounds;

    private BitmapManager mBitmapManager;

    private float mPercent;
    private int mAnimIndex;
    private int mRefreshIndex;


    public MeiTuanLoadingView(Context context) {
        this(context, null);
    }

    public MeiTuanLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeiTuanLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initBitmap();
    }

    private void initView() {
        mBitmapPaint = new Paint();
        mBitmapPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    private void initBitmap() {
        mBitmapManager = new BitmapManager();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ensureBounds(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * 107f/ 82f);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private void ensureBounds(int widthMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * 107f/ 82f);
        int pullHeight = (int)(width * 60f/82f);
        mDrawBounds = new RectF();
        mDrawBounds.left = 0;
        mDrawBounds.top = 0;
        mDrawBounds.right = width;
        mDrawBounds.bottom = height;

        mPullDrawBounds = new RectF();
        mPullDrawBounds.left = 0;
        mPullDrawBounds.right = width;
        mPullDrawBounds.top = mDrawBounds.centerY() - pullHeight / 2.0f;
        mPullDrawBounds.bottom = mDrawBounds.centerY() + pullHeight / 2.0f;
    }

    private RectF getPercentRect() {
        RectF rectF = new RectF();
        rectF.left = mPullDrawBounds.centerX() - (mPullDrawBounds.width() * mPercent)/ 2;
        rectF.right = mPullDrawBounds.centerX() + (mPullDrawBounds.width() * mPercent)/ 2;
        rectF.top = mPullDrawBounds.centerY() - (mPullDrawBounds.height() * mPercent)/ 2;
        rectF.bottom = mPullDrawBounds.centerY() + (mPullDrawBounds.height() * mPercent)/ 2;
        return rectF;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mState) {
            case STATE_PULL:
                canvas.drawBitmap(mBitmapManager.mPullBitmap,
                        null, getPercentRect(), mBitmapPaint);
                break;
            case STATE_PULL_ANIM:
                int index = mAnimIndex++ / 6;
                canvas.drawBitmap(mBitmapManager.mPullEndBitmaps[Math.min(index, 4)],
                        null, mDrawBounds, mBitmapPaint);
                invalidate();
                break;
            case STATE_REFRESHING:
                index = mRefreshIndex++ / 6;
                canvas.drawBitmap(mBitmapManager.mRefreshingBitmaps[index % 7],
                        null, mDrawBounds, mBitmapPaint);
                invalidate();
                break;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        recycleBitmaps();
        super.onDetachedFromWindow();
    }

    private void recycleBitmaps() {
        mBitmapManager.recycle();
    }

    @Override
    public void setPercent(float percent) {
        this.mPercent = percent;
        invalidate();
    }

    @Override
    public void setRefreshing(boolean bRefresh) {
        if (bRefresh) {
            mState = STATE_REFRESHING;
        }else {
            mState = STATE_PULL;
            mAnimIndex = 0;
        }
    }

    @Override
    public boolean isRefreshing() {
        return mState == STATE_REFRESHING;
    }

    @Override
    public void onLooseRefresh() {
        mState = STATE_PULL_ANIM;
    }

    private class BitmapManager {
        public Bitmap mPullBitmap;
        public Bitmap[] mPullEndBitmaps;
        public Bitmap[] mRefreshingBitmaps;

        public BitmapManager() {
            initBitmaps();
        }

        private void initBitmaps() {
            mPullBitmap = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    R.drawable.pull_image
            );
            mPullEndBitmaps = new Bitmap[5];
            mPullEndBitmaps[0] = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    R.drawable.pull_end_image_frame_01
            );
            mPullEndBitmaps[1] = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    R.drawable.pull_end_image_frame_02
            );
            mPullEndBitmaps[2] = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    R.drawable.pull_end_image_frame_03
            );
            mPullEndBitmaps[3] = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    R.drawable.pull_end_image_frame_04
            );
            mPullEndBitmaps[4] = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    R.drawable.pull_end_image_frame_05
            );

            mRefreshingBitmaps = new Bitmap[8];
            mRefreshingBitmaps[0] = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    R.drawable.refreshing_image_frame_01
            );
            mRefreshingBitmaps[1] = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    R.drawable.refreshing_image_frame_02
            );
            mRefreshingBitmaps[2] = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    R.drawable.refreshing_image_frame_03
            );
            mRefreshingBitmaps[3] = mRefreshingBitmaps[1];

            mRefreshingBitmaps[4] = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    R.drawable.refreshing_image_frame_05
            );
            mRefreshingBitmaps[5] = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    R.drawable.refreshing_image_frame_06
            );
            mRefreshingBitmaps[6] = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    R.drawable.refreshing_image_frame_07
            );
            mRefreshingBitmaps[7] = mRefreshingBitmaps[5];

        }

        void recycle() {

        }


    }
}
