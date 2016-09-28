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
import com.alious.pro.pulltorefresh.library.Utils;
import com.alious.pro.pulltorefresh.library.interfaces.IPercentView;
import com.alious.pro.pulltorefresh.library.spirits.RotateSpirit;
import com.alious.pro.pulltorefresh.library.spirits.Spirit;
import com.alious.pro.pulltorefresh.library.spirits.SpringSpirit;
import com.alious.pro.pulltorefresh.library.spirits.TranslateSpirit;

import java.util.ArrayList;

/**
 * Baidu loading view
 *
 * Created by aliouswang on 16/9/27.
 */

public class BaiduLoadingView extends View implements IPercentView{

    private Paint mPaint;
    private Context mContext;

    private Bitmap mLogoBitmap;
    private Bitmap mWheelBitmap;
    private Bitmap mSunBitmap;
    private Bitmap mBackBitmap;

    private RectF mTotalBound;

    private RectF mRiderRect;
    private RectF mLeftWheelRect;
    private RectF mRightWheelRect;
    private RectF mSunRect;

    private SpringSpirit mRiderSpirit;
    private RotateSpirit mLeftWheelSpirit;
    private RotateSpirit mRightWheelSpirit;
    private RotateSpirit mSunSpirit;

    private BackgroundSpirit mBackgroundSpirit;

    public BaiduLoadingView(Context context) {
        this(context, null);
    }

    public BaiduLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaiduLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        initBitmaps();
    }

    private void initBitmaps() {
        mLogoBitmap = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.pull_rider);
        mWheelBitmap = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.pull_wheel);
        mSunBitmap = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.pull_sun);
        mBackBitmap = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.pull_back);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ensureBounds(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width;
        setMeasuredDimension(width, height);
    }

    private void ensureBounds(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width;

        mTotalBound = new RectF();
        mTotalBound.left = 0;
        mTotalBound.top = 0;
        mTotalBound.right = width;
        mTotalBound.bottom = height;

        float riderWidth = Utils.dipToPx(mContext, 40);
        mRiderRect = new RectF();
        mRiderRect.left = mTotalBound.centerX() - riderWidth / 2;
        mRiderRect.right = mTotalBound.centerX() + riderWidth / 2;
        mRiderRect.top = mTotalBound.centerY() - riderWidth / 2;
        mRiderRect.bottom = mTotalBound.centerY() + riderWidth / 2;

        float whellWidth = Utils.dipToPx(mContext, 10);
        mLeftWheelRect = new RectF();
        mLeftWheelRect.left = mRiderRect.left + whellWidth / 2;
        mLeftWheelRect.right = mRiderRect.left + whellWidth + whellWidth/2;
        mLeftWheelRect.top = mRiderRect.bottom - whellWidth / 2;
        mLeftWheelRect.bottom = mRiderRect.bottom + whellWidth / 2;

        mRightWheelRect = new RectF();
        mRightWheelRect.left = mRiderRect.right - whellWidth / 2 - whellWidth;
        mRightWheelRect.right = mRiderRect.right - whellWidth / 2;
        mRightWheelRect.top = mRiderRect.bottom - whellWidth / 2;
        mRightWheelRect.bottom = mRiderRect.bottom + whellWidth / 2;

        float sunWidth = Utils.dipToPx(mContext, 15);
        mSunRect = new RectF();
        mSunRect.left = mRiderRect.right + sunWidth;
        mSunRect.right = mRiderRect.right + sunWidth * 2;
        mSunRect.top = mRiderRect.top - sunWidth * 2;
        mSunRect.bottom = mRiderRect.top - sunWidth ;

        initSpirits();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRiderSpirit.draw(canvas);
        mLeftWheelSpirit.draw(canvas);
        mRightWheelSpirit.draw(canvas);
        mSunSpirit.draw(canvas);
        mBackgroundSpirit.draw(canvas);
        invalidate();
    }

    private void initSpirits() {
        if (mRiderSpirit == null) {
            mRiderSpirit = new SpringSpirit(mContext, mRiderRect, R.drawable.pull_rider);
            mLeftWheelSpirit = new RotateSpirit(mContext, mLeftWheelRect, R.drawable.pull_wheel);
            mRightWheelSpirit = new RotateSpirit(mContext, mRightWheelRect, R.drawable.pull_wheel);
            mSunSpirit = new RotateSpirit(mContext, mSunRect, R.drawable.pull_sun);
            mSunSpirit.setAngelDelta(3);

            mBackgroundSpirit = new BackgroundSpirit();
        }else {
            mRiderSpirit.setDrawRect(mRiderRect);
            mLeftWheelSpirit.setDrawRect(mLeftWheelRect);
            mRightWheelSpirit.setDrawRect(mRightWheelRect);
            mSunSpirit.setDrawRect(mSunRect);

            mBackgroundSpirit.update();
        }

    }

    @Override
    public void setPercent(float percent) {

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

    /**
     * Background spirit
     */
    private class BackgroundSpirit {
        ArrayList<TranslateSpirit> mSpirits =
                new ArrayList<>(2);
        public BackgroundSpirit() {
            float width = mTotalBound.width();
            float height = width * 167.0f / 600.0f;
            for (int i = 0; i < 2; i ++) {
                RectF rectF = new RectF();
                rectF.left = mTotalBound.left + width * i;
                rectF.right = mTotalBound.right + width * (i + 1);
                rectF.top = mRiderRect.bottom - height;
                rectF.bottom = mRiderRect.bottom;
                TranslateSpirit spirit = new TranslateSpirit(mContext, rectF, R.drawable.pull_back);
                if (i == 0) {
                    spirit.setStartTranslate(0);
                    spirit.setEndTranslate(-width);
                }else {
                    spirit.setStartTranslate(width);
                    spirit.setEndTranslate(0);
                }
                mSpirits.add(spirit);
            }
        }

        public void update() {
            float width = mTotalBound.width();
            float height = width * 167.0f / 600.0f;
            for (int i = 0; i < 2; i ++) {
                RectF rectF = new RectF();
                rectF.left = mTotalBound.left + width * i;
                rectF.right = mTotalBound.right + width * (i + 1);
                rectF.top = mRiderRect.bottom - height;
                rectF.bottom = mRiderRect.bottom;
                mSpirits.get(i).setDrawRect(rectF);
            }
        }

        public void draw(Canvas canvas) {
            for (Spirit spirit : mSpirits) {
                spirit.draw(canvas);
            }
        }
    }
}
