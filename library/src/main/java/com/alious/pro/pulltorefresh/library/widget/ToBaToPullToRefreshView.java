package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.alious.pro.pulltorefresh.library.R;
import com.alious.pro.pulltorefresh.library.interfaces.IPercentView;

/**
 * To ba to pull to refresh view.
 *
 * Created by aliouswang on 16/9/19.
 */
public class ToBaToPullToRefreshView extends PercentPullToRefreshView{

    public ToBaToPullToRefreshView(Context context) {
        super(context);
    }

    public ToBaToPullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToBaToPullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getInflateLayout() {
        return R.layout.layout_tobato_pull_loading;
    }

    @Override
    protected IPercentView getPercentView() {
        return (IPercentView) mPullLoadingView.findViewById(R.id.tobato_loading);
    }
}
