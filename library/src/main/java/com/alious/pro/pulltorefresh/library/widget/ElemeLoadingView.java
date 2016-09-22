package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;

import com.alious.pro.pulltorefresh.library.R;
import com.alious.pro.pulltorefresh.library.interfaces.IPercentView;

/**
 * Eleme loading view.
 *
 * Created by aliouswang on 16/9/21.
 */

public class ElemeLoadingView extends View implements IPercentView{

    private Context mContext;
    private BitmapManager mBitmapManager;

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
            bitmap
        }



    }
}
