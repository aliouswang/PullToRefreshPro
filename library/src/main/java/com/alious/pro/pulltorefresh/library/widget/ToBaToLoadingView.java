package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.alious.pro.pulltorefresh.library.R;
import com.alious.pro.pulltorefresh.library.Utils;
import com.alious.pro.pulltorefresh.library.interfaces.IPercentView;

/**
 * To-ba-to loading view
 * <p/>
 * Created by aliouswang on 16/9/13.
 */
public class ToBaToLoadingView extends View implements IPercentView{

    public static final float CIRCLE_STROKE_WIDTH = 1.5f;

    private Context mContext;

    private Paint mCirclePaint;
    private Paint mBitmapPaint;

    private float percent = 0.5f;
    private int startAngel = -90;
    private int endAngel = 0;

    private RectF mBounds;
    private Bitmap mCenterBitmap;
    private Bitmap mLoadingBitmap;

    private Rect mCenterSrcRect;
    private RectF mCenterDestRect;
    private int mCenterBitmapDrawWidth;

    private PorterDuffXfermode mSrcInXfermode;

    private boolean mRefreshing;

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




    private void initView() {
        mContext = getContext();
        mSrcInXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mCenterBitmap =
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_circle_refresh);
        mLoadingBitmap = BitmapFactory
                .decodeResource(getResources(), R.drawable.ic_circle_gray);

        mCirclePaint = new Paint();
        mCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(Utils.dipToPx(mContext, CIRCLE_STROKE_WIDTH));
        mCirclePaint.setColor(ContextCompat.getColor(mContext, R.color.colorBBBBBB));

        mBitmapPaint = new Paint();
        mBitmapPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
//        mBitmapPaint.setAlpha(0);
//        mBitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        mBitmapPaint.setStyle(Paint.Style.STROKE);
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
            mBounds.left = 15;
            mBounds.top = 15;
            mBounds.right = width - 30;
            mBounds.bottom = width - 30;

            mCenterBitmapDrawWidth = Utils.dipToPx(getContext(), 8);
            mCenterSrcRect = new Rect(0, 0, mCenterBitmapDrawWidth, mCenterBitmapDrawWidth);
            mCenterDestRect = new RectF();
            mCenterDestRect.left = mBounds.left + mCenterBitmapDrawWidth;
            mCenterDestRect.top = mBounds.top + mCenterBitmapDrawWidth;
            mCenterDestRect.right = mBounds.right - mCenterBitmapDrawWidth;
            mCenterDestRect.bottom = mBounds.bottom - mCenterBitmapDrawWidth;
        }
    }

    int rotateDegress = 0;
    @Override
    protected void onDraw(Canvas canvas) {

        if (!isRefreshing()) {
            if (rotateDegress != 0) {
                rotateDegress = 0;
            }
            canvas.drawArc(mBounds, startAngel, endAngel, false, mCirclePaint);

            int save = canvas.saveLayer(0, 0, mBounds.width(), mBounds.height(), null, Canvas.ALL_SAVE_FLAG);

            mBitmapPaint.setAlpha(255);
            mBitmapPaint.setXfermode(null);
            mBitmapPaint.setStyle(Paint.Style.FILL);
            canvas.drawBitmap(mCenterBitmap, null, mCenterDestRect, mBitmapPaint);

            mBitmapPaint.setAlpha(0);
            mBitmapPaint.setXfermode(mSrcInXfermode);
            canvas.drawRect(mCenterDestRect.left,
                    mCenterDestRect.top + (percent * mCenterDestRect.height()),
                    mCenterDestRect.right,
                    mCenterDestRect.bottom,
                    mBitmapPaint);

            //restore to canvas
            canvas.restoreToCount(save);

        } else {
            rotateDegress += 4;
            canvas.rotate(rotateDegress, mBounds.centerX(), mBounds.centerY());
            mBitmapPaint.setAlpha(255);
            mBitmapPaint.setXfermode(null);
            mBitmapPaint.setStyle(Paint.Style.FILL);
            canvas.drawBitmap(mLoadingBitmap, null, mBounds, mBitmapPaint);
            invalidate();
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean isRefreshing() {
        return mRefreshing;
    }

    @Override
    public void onLooseRefresh() {

    }

    @Override
    public void setPercent(float percent) {
        this.percent = percent;
        endAngel = (int) (360 * percent);
        invalidate();
    }

    public void setRefreshing(boolean bRefresh) {
        mRefreshing = bRefresh;
        invalidate();
    }
}
