package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.alious.pro.pulltorefresh.library.R;
import com.alious.pro.pulltorefresh.library.interfaces.IPercentView;
import com.alious.pro.pulltorefresh.library.listener.OnRefreshListener;

/**
 * Percent pull to refresh view.
 *
 * Created by aliouswang on 16/9/13.
 */
public abstract class PercentPullToRefreshView extends BasePullToRefreshView{

    private IPercentView mIPercentView;
    private TextView tv_loading;

    public PercentPullToRefreshView(Context context) {
        super(context);
    }

    public PercentPullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PercentPullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        mIPercentView = getPercentView();
        tv_loading = (TextView) mPullLoadingView.findViewById(R.id.tv_loading);

        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                tv_loading.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setRefresh(false);
                    }
                }, 4000);
            }
        });
    }

    @Override
    protected abstract int getInflateLayout();

    protected abstract IPercentView getPercentView();

    //    @Override
//    protected int getInflateLayout() {
//        return R.layout.layout_tobato_pull_loading;
//    }

    @Override
    protected void onPullYPercent(float yDistance) {
        super.onPullYPercent(yDistance);
        mIPercentView.setPercent(yDistance);
    }

    @Override
    protected int getPullRefreshDistance() {
        return 120;
    }

    @Override
    protected void onRefreshing() {
        super.onRefreshing();
        mIPercentView.setRefreshing(true);
        tv_loading.setText(BasePullToRefreshView.REFRESHING);
        mIPercentView.setRefreshing(true);
    }

    @Override
    protected void onRestore() {
        super.onRestore();
        mIPercentView.setRefreshing(false);
        tv_loading.setText(BasePullToRefreshView.PULL_TO_REFRESH);
    }

    @Override
    protected void onPullDown() {
        super.onPullDown();
        tv_loading.setText(BasePullToRefreshView.PULL_TO_REFRESH);
        mIPercentView.setRefreshing(false);
    }

    @Override
    protected void onLooseRefresh() {
        super.onLooseRefresh();
        tv_loading.setText(BasePullToRefreshView.LOOSE_TO_REFRESH);
        mIPercentView.onLooseRefresh();
    }
}
