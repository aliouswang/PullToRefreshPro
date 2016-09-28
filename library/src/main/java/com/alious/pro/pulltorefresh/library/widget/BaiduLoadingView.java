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
        ensureBounds(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width;
        setMeasuredDimension(width, height);
    }

    private void ensureBounds(int widthMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width;

        mTotalBound = new RectF();
        mTotalBound.left = 0;
        mTotalBound.top = 0;
        mTotalBound.right = width;
        mTotalBound.bottom = height * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap();
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
}
