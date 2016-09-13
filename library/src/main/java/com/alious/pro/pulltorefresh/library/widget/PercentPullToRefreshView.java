package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.alious.pro.pulltorefresh.library.R;
import com.alious.pro.pulltorefresh.library.listener.OnRefreshListener;

/**
 * Created by aliouswang on 16/9/13.
 */
public class PercentPullToRefreshView extends BasePullToRefreshView{

    private ToBaToLoadingView mToBaToLoadingView;
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
        mToBaToLoadingView = (ToBaToLoadingView)
                mPullLoadingView.findViewById(R.id.tobato_loading);
        tv_loading = (TextView) mPullLoadingView.findViewById(R.id.tv_loading);

        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                tv_loading.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setRefresh(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    protected int getInflateLayout() {
        return R.layout.layout_tobato_pull_loading;
    }

    @Override
    protected void onPullYPercent(float yDistance) {
        super.onPullYPercent(yDistance);
        mToBaToLoadingView.setPercent(yDistance);
    }

    @Override
    protected int getPullRefreshDistance() {
        return 120;
    }

    @Override
    protected void onRefreshing() {
        super.onRefreshing();
        mToBaToLoadingView.setRefreshing(true);
    }

    @Override
    protected void onRestore() {
        super.onRestore();
        mToBaToLoadingView.setRefreshing(false);
    }

    @Override
    protected void onPullDown() {
        super.onPullDown();
        tv_loading.setText(BasePullToRefreshView.PULL_TO_REFRESH);
    }

    @Override
    protected void onLooseRefresh() {
        super.onLooseRefresh();
        tv_loading.setText(BasePullToRefreshView.LOOSE_TO_REFRESH);
    }
}
