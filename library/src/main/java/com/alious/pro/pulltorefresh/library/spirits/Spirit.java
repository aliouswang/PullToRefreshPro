package com.alious.pro.pulltorefresh.library.spirits;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by aliouswang on 16/9/28.
 */

public class Spirit implements IDraw{

    protected Context mContext;

    protected Paint mPaint;

    protected RectF mDrawRect;

    protected int mBitmapResId;

    protected Bitmap mBitmap;

    public Spirit(Context context, RectF rect, int resId) {
        this.mContext = context;
        this.mDrawRect = rect;
        this.mBitmapResId = resId;
        mPaint = new Paint();
        initParams();
    }

    private void initParams() {
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(),
                this.mBitmapResId);
    }

    public void setDrawRect(RectF drawRect) {
        mDrawRect = drawRect;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, null, mDrawRect, mPaint);
    }
}
