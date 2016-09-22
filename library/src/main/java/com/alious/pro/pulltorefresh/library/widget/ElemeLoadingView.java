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

import java.util.ArrayList;

/**
 * Eleme loading view.
 *
 * Created by aliouswang on 16/9/21.
 */

public class ElemeLoadingView extends View implements IPercentView{

    private static final int STATE_PULL = 0x01;
    private static final int STATE_PULL_ANIM = 0x02;
    private static final int STATE_REFRESHING = 0x03;

    private static final int SPRINT_SIDE_LEFT = 0x01;
    private static final int SPRINT_SIDE_RIGHT = 0X02;

    private int mState = STATE_PULL;

    private Context mContext;
    private BitmapManager mBitmapManager;
    private Paint mPaint;

    private RectF mTotalBound;
    private RectF mLogoRectF;
    private RectF mBottomRectF;
    private RectF mLeftHandRectF;
    private RectF mRightHandRectF;

    private RectF mSprintRectF;

    private int mRotateDegree;
    private float mSprintWidth;
    private float mSprintRadius;

    private float mSpringIndex;

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
        mSprintWidth = Utils.dipToPx(mContext, 30);
        mSprintRadius = Utils.dipToPx(mContext,30);
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
        float padding = Utils.dipToPx(mContext, 60);

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

        mSprintRectF = new RectF();
        mSprintRectF.left = mLogoRectF.centerX() - mSprintWidth/ 2;
        mSprintRectF.right = mLogoRectF.centerX() + mSprintWidth/ 2;
        mSprintRectF.top = mLogoRectF.top - mSprintWidth/ 2;
        mSprintRectF.bottom = mLogoRectF.top + mSprintWidth/ 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mState == STATE_REFRESHING) {
            drawSprints(canvas);
        }

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

    private void drawSprints(Canvas canvas) {
        int sprintSize = mSprints.size();
        if (sprintSize < 1) {
            Sprint sprint = new Sprint(SPRINT_SIDE_LEFT, mBitmapManager.bitmapFlower);
            mSprints.add(sprint);
        }else if (sprintSize < 2) {
            if (mSpringIndex > 20) {
                Sprint sprint = new Sprint(SPRINT_SIDE_RIGHT, mBitmapManager.bitmapChiken);
                mSprints.add(sprint);
            }
        }else if (sprintSize < 3) {
            if (mSpringIndex > 40) {
                Sprint sprint = new Sprint(SPRINT_SIDE_LEFT, mBitmapManager.bitmapBlue);
                mSprints.add(sprint);
            }
        }else if (sprintSize < 4) {
            if (mSpringIndex > 60) {
                Sprint sprint = new Sprint(SPRINT_SIDE_RIGHT, mBitmapManager.bitmapHanm);
                mSprints.add(sprint);
            }
        }else if (sprintSize < 5) {
            if (mSpringIndex > 80) {
                Sprint sprint = new Sprint(SPRINT_SIDE_LEFT, mBitmapManager.bitmapOrange);
                mSprints.add(sprint);
            }
        }else if (sprintSize < 6) {
            if (mSpringIndex > 100) {
                Sprint sprint = new Sprint(SPRINT_SIDE_RIGHT, mBitmapManager.bitmapPear);
                mSprints.add(sprint);
            }
        }

        for (Sprint sprint : mSprints) {
            sprint.drawSelf(canvas);
        }

        mSpringIndex ++;
        invalidate();
    }

    private ArrayList<Sprint> mSprints = new ArrayList<>();

    private class Sprint {

        private float degree;
        private int side;
        private Bitmap mBitmap;

        public Sprint(int side, Bitmap bitmap) {
            this.side = side;
            this.mBitmap = bitmap;
        }

        public void drawSelf(Canvas canvas) {
            RectF newRectF = new RectF();
            newRectF.top = (float) (mSprintRectF.top - mSprintRadius * Math.sin(degree));
            newRectF.bottom = newRectF.top + mSprintRectF.height();
            if (this.side == SPRINT_SIDE_RIGHT) {
                newRectF.left = (float) (mSprintRectF.left + (mSprintRadius - mSprintRadius * Math.cos(degree)));
            }else {
                newRectF.left = (float) (mSprintRectF.left - (mSprintRadius - mSprintRadius * Math.cos(degree)));
            }
            newRectF.right = newRectF.left + mSprintRectF.width();
            canvas.drawBitmap(mBitmap,
                    null, newRectF, mPaint);
            degree += Math.PI/ 60.0f;
            if (degree >= Math.PI) {
                degree = 0;
            }
        }
    }



    @Override
    public void setPercent(float percent) {
        mRotateDegree = (int) (percent * 90.0f);
        invalidate();
    }

    @Override
    public void setRefreshing(boolean bRefresh) {
        if (bRefresh) {
            mState = STATE_REFRESHING;
            invalidate();
        }else {
            mState = STATE_PULL;
            mSpringIndex = 0;
            mSprints.clear();
        }
    }

    @Override
    public boolean isRefreshing() {
        return false;
    }

    @Override
    public void onLooseRefresh() {
        mState = STATE_PULL_ANIM;
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
            bitmapChiken = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_chicken
            );
            bitmapFlower = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_flower
            );
            bitmapHanm = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_hanm
            );
            bitmapOrange = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_orange
            );
            bitmapPear = BitmapFactory.decodeResource(
                    resources, R.drawable.ele_pear
            );
        }



    }
}
