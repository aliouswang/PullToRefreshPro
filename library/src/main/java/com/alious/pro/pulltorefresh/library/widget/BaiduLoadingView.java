package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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
