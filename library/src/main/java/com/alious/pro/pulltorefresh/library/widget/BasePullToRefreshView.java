package com.alious.pro.pulltorefresh.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Base pull to refresh view
 *
 * Created by aliouswang on 16/9/12.
 */
public class BasePullToRefreshView extends LinearLayout{

    public BasePullToRefreshView(Context context) {
        this(context, null);
    }

    public BasePullToRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasePullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

    }

    private float mLastScrollX = 0f;
    private float mLastScrollY = 0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float curX = event.getX();
        float curY = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                if (mLastScrollY == 0f) {
                    mLastScrollY = curY;
                }
                scrollBy(0, (int) (mLastScrollY - curY));
                mLastScrollY = curY;
                break;
            case MotionEvent.ACTION_UP:
                mLastScrollY = 0f;
                break;
        }
        return super.onTouchEvent(event);
    }
}
