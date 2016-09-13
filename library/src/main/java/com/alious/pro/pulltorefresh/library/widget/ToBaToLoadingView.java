package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.alious.pro.pulltorefresh.library.R;
import com.alious.pro.pulltorefresh.library.Utils;

/**
 * To-ba-to loading view
 *
 * Created by aliouswang on 16/9/13.
 */
public class ToBaToLoadingView extends View{

    public static final int CIRCLE_STROKE_WIDTH = 3;

    private Context mContext;

    private Paint mCirclePaint;
    private Paint mClearPaint;

    private float percent = 0.5f;
    private int startAngel = -90;
    private int endAngel = 0;

    private RectF mBounds;
    private Bitmap mCenterBitmap;

    private Rect mCenterSrcRect;
    private RectF mCenterDestRect;
    private int mCenterBitmapDrawWidth;

    public ToBaToLoadingView(Context context) {
        this(context, null);
    }

    public ToBaToLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToBaToLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
        endAngel = (int) (360 * percent);
        invalidate();
    }

    private void initView() {
        mContext = getContext();

        mCenterBitmap =
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_circle_refresh);

        mCirclePaint = new Paint();
        mCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(Utils.dipToPx(mContext, CIRCLE_STROKE_WIDTH));
        mCirclePaint.setColor(ContextCompat.getColor(mContext, R.color.colorBBBBBB));

//        mClearPaint = new Paint();
//        mClearPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
//        mClearPaint.setAlpha(0);
////        mClearPaint.setColor(ContextCompat.getColor(mContext, android.R.color.transparent));
//        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        mClearPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ensureBounds(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void ensureBounds(int widthMeasureSpec, int heightMeasureSpec) {
        if (mBounds == null) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            mBounds = new RectF();
            mBounds.left = 10;
            mBounds.top = 10;
            mBounds.right = width - 20;
            mBounds.bottom = width - 20;

            mCenterBitmapDrawWidth = Utils.dipToPx(getContext(), 8);
            mCenterSrcRect = new Rect(0, 0, mCenterBitmapDrawWidth, mCenterBitmapDrawWidth);
            mCenterDestRect = new RectF();
            mCenterDestRect.left = mBounds.left + mCenterBitmapDrawWidth;
            mCenterDestRect.top = mBounds.top + mCenterBitmapDrawWidth;
            mCenterDestRect.right = mBounds.right - mCenterBitmapDrawWidth;
            mCenterDestRect.bottom = mBounds.bottom - mCenterBitmapDrawWidth;

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (percent <= 1.0f) {
            canvas.drawArc(mBounds, startAngel, endAngel, false, mCirclePaint);

            canvas.drawBitmap(mCenterBitmap, null, mCenterDestRect, mCirclePaint);

//            mCirclePaint.setAlpha(0);
//            mCirclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//            canvas.drawCircle(mBounds.centerX(),
//                    mBounds.centerY(),
//                    (mBounds.width()/2 - Utils.dipToPx(mContext, CIRCLE_STROKE_WIDTH)),
//                    mCirclePaint);
//            mCirclePaint.setXfermode(null);
//            canvas.drawArc(0, 0, 30, 30, 0, 180, true, mCirclePaint);
//            canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), mBounds.width()/ 2, mCirclePaint);
        }else {

        }
    }
}
