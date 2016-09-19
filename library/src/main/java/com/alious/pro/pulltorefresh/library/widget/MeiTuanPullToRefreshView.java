package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.alious.pro.pulltorefresh.library.R;
import com.alious.pro.pulltorefresh.library.interfaces.IPercentView;

/**
 * Mei Tuan pull to refresh view.
 *
 * Created by aliouswang on 16/9/19.
 */
public class MeiTuanPullToRefreshView extends PercentPullToRefreshView{

    public MeiTuanPullToRefreshView(Context context) {
        super(context);
    }

    public MeiTuanPullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeiTuanPullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getInflateLayout() {
        return R.layout.layout_meituan_pull_loading;
    }

    @Override
    protected IPercentView getPercentView() {
        return (IPercentView) mPullLoadingView.findViewById(R.id.meituan_loading);
    }
}
