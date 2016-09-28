package com.alious.pro.pulltorefresh.library.spirits;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by aliouswang on 16/9/28.
 */

public class Spirit {

    protected Paint mPaint;

    protected Rect mDrawRect;

    protected int mBitmapResId;

    protected Bitmap mBitmap;

    public Spirit(Rect rect, int resId) {
        this.mDrawRect = rect;
        this.mBitmapResId = resId;
        mPaint = new Paint();
        initParams();
    }

    private void initParams() {

    }


}
