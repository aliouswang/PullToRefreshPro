package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.alious.pro.pulltorefresh.library.R;

/**
 * Created by aliouswang on 16/9/12.
 */
public class DefaultPullToRefreshView extends BasePullToRefreshView{

    public DefaultPullToRefreshView(Context context) {
        super(context);
    }

    public DefaultPullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultPullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getInflateLayout() {
        return R.layout.layout_pull_loading;
    }


}
