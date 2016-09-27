package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.alious.pro.pulltorefresh.library.interfaces.IPercentView;

/**
 * Baidu loading view
 *
 * Created by aliouswang on 16/9/27.
 */

public class BaiduLoadingView extends View implements IPercentView{

    public BaiduLoadingView(Context context) {
        this(context, null);
    }

    public BaiduLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaiduLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
