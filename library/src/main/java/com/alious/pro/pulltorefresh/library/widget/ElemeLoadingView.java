package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.alious.pro.pulltorefresh.library.R;
import com.alious.pro.pulltorefresh.library.Utils;
import com.alious.pro.pulltorefresh.library.interfaces.IPercentView;

/**
 * Eleme loading view.
 *
 * Created by aliouswang on 16/9/21.
 */

public class ElemeLoadingView extends View implements IPercentView{

    private Context mContext;
    private BitmapManager mBitmapManager;
    private Paint mPaint;

    private RectF mTotalBound;
    private RectF mLogoRectF;
    private RectF mBottomRectF;
    private RectF mLeftHandRectF;
    private RectF mRightHandRectF;

    private int mRotateDegree;

    public ElemeLoadingView(Context context) {
        this(context, null);
    }

    public ElemeLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElemeLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mBitmapManager = new BitmapManager();
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ensureBounds(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width;
        setMeasuredDimension(width, height);
    }

    private void ensureBounds(int widthMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width;
        int pullHeight = (int)(width * 60f/82f);

        mTotalBound = new RectF();
        mTotalBound.left = 0;
        mTotalBound.top = 0;
        mTotalBound.right = width;
        mTotalBound.bottom = height;

        float logoWidth = Utils.dipToPx(mContext, 30);
        float handWidth = Utils.dipToPx(mContext, 6);
        float handHeight = Utils.dipToPx(mContext, 20);
        float padding = Utils.dipToPx(mContext, 30);

        mLogoRectF = new RectF();
        mLogoRectF.left = mTotalBound.centerX() - logoWidth/2;
        mLogoRectF.right = mTotalBound.centerX() + logoWidth/2;
        mLogoRectF.top = mTotalBound.bottom - padding - logoWidth;
        mLogoRectF.bottom = mTotalBound.bottom - padding;

        mBottomRectF = new RectF();
        mBottomRectF.left = mLogoRectF.left - logoWidth/5;
        mBottomRectF.right = mLogoRectF.right + logoWidth/5;
        mBottomRectF.top = mLogoRectF.bottom - logoWidth/10;
        mBottomRectF.bottom = mLogoRectF.bottom + logoWidth/5;

        mLeftHandRectF = new RectF();
        mLeftHandRectF.left = mLogoRectF.left;
        mLeftHandRectF.right = mLogoRectF.left + handWidth;
        mLeftHandRectF.top = mLogoRectF.top - handHeight + handHeight/10;
        mLeftHandRectF.bottom = mLogoRectF.top + handHeight/10;


        mRightHandRectF = new RectF();
        mRightHandRectF.left = mLogoRectF.right - handWidth;
        mRightHandRectF.right = mLogoRectF.right;
        mRightHandRectF.top = mLogoRectF.top - handHeight + handHeight/10;
        mRightHandRectF.bottom = mLogoRectF.top + handHeight/10;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmapManager.bitmapBottom,
                null, mBottomRectF, mPaint);

        canvas.save();
        canvas.rotate(-mRotateDegree, mLeftHandRectF.centerX(), mLeftHandRectF.bottom);
        canvas.drawBitmap(mBitmapManager.bitmapLeftHand,
                null, mLeftHandRectF, mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(mRotateDegree, mRightHandRectF.centerX(), mRightHandRectF.bottom);
        canvas.drawBitmap(mBitmapManager.bitmapRightHand,
                null, mRightHandRectF, mPaint);
        canvas.restore();

        canvas.drawBitmap(mBitmapManager.bitmapLogo,
                null, mLogoRectF, mPaint);
    }

    @Override
    public void setPercent(float percent) {
        mRotateDegree = (int) (percent * 90.0f);
        invalidate();
    }

    @Override
    public void setRefreshing(boolean bRefresh) {

    }

    @Override
    public boolean isRefreshing() {
        return false;
    }

    @Override
    public void onLooseRefresh() {

    }

    private class BitmapManager {

        public Bitmap bitmapLogo;
        public Bitmap bitmapBottom;
        public Bitmap bitmapLeftHand;
        public Bitmap bitmapRightHand;
        public Bitmap bitmapSlogan;

        public Bitmap bitmapBlue;
        public Bitmap bitmapChiken;
        public Bitmap bitmapFlower;
        public Bitmap bitmapHanm;
        public Bitmap bitmapOrange;
        public Bitmap bitmapPear;

        public BitmapManager() {
            initBitmap();
        }

        private void initBitmap() {
            Resources resources = mContext.getResources();
            bitmapLogo = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_logo);
            bitmapLeftHand = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_left_hand);
            bitmapRightHand = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_right_hand
            );
            bitmapSlogan = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_slogan
            );
            bitmapBottom = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_bottom
            );

            bitmapBlue = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_blue
            );
            bitmapBlue = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_chicken
            );
            bitmapBlue = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_flower
            );
            bitmapBlue = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_hanm
            );
            bitmapBlue = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_orange
            );
            bitmapBlue = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_pear
            );
        }



    }
}
