package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alious.pro.pulltorefresh.library.R;
import com.alious.pro.pulltorefresh.library.listener.OnRefreshListener;

/**
 * Created by aliouswang on 16/9/12.
 */
public class DefaultPullToRefreshView extends BasePullToRefreshView {

    private ImageView img_loading;
    private ProgressBar pb_loading;
    private TextView tv_loading;

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
        return R.layout.layout_default_pull_loading;
    }

    @Override
    protected void initView() {
        super.initView();
        img_loading = (ImageView) mPullLoadingView.findViewById(R.id.img_loading);
        pb_loading = (ProgressBar) mPullLoadingView.findViewById(R.id.pb_loading);
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
    protected void onRestore() {
        super.onRestore();
        img_loading.setVisibility(VISIBLE);
        pb_loading.setVisibility(GONE);
        tv_loading.setText(BasePullToRefreshView.PULL_TO_REFRESH);
    }

    @Override
    protected void onPullDown() {
        super.onPullDown();
        final RotateAnimation animation = new RotateAnimation(180f, 360f,
                Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(300);//设置动画持续时间
        animation.setFillAfter(true);
        img_loading.startAnimation(animation);
        tv_loading.setText(BasePullToRefreshView.PULL_TO_REFRESH);
    }

    @Override
    protected void onLooseRefresh() {
        super.onLooseRefresh();
        final RotateAnimation animation = new RotateAnimation(0f, 180f,
                Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(300);//设置动画持续时间
        animation.setFillAfter(true);
        img_loading.startAnimation(animation);
        tv_loading.setText(BasePullToRefreshView.LOOSE_TO_REFRESH);
    }

    @Override
    protected void onRefreshing() {
        super.onRefreshing();
        img_loading.clearAnimation();
        img_loading.setVisibility(GONE);
        pb_loading.setVisibility(VISIBLE);
        tv_loading.setText(BasePullToRefreshView.REFRESHING);
    }
}
