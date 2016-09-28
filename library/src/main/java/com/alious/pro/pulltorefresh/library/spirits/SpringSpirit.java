package com.alious.pro.pulltorefresh.library.spirits;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Spring spirit
 *
 * Created by aliouswang on 16/9/28.
 */

public class SpringSpirit extends Spirit{

    public static final int SPRING_HORIZONTAL = 0x01;
    public static final int SPRING_VERTICAL = 0x02;

    public static final float DEFAULT_STEP_DELTA = 0.4f;
    public static final float DEFAULT_MAX_SETP = 5.0f;

    private int mSpringType = SPRING_VERTICAL;
    private float mDelta = DEFAULT_STEP_DELTA;

    private float mTransilation = 0;

    private float mStepIndex = 0;
    private float mStepFlag = 1.0f;

    public SpringSpirit(Context context, RectF rect, int resId) {
        super(context, rect, resId);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        tanslate(canvas);
        super.draw(canvas);
        canvas.restore();
        increace();
    }

    @Override
    protected void tanslate(Canvas canvas) {
        if (mSpringType == SPRING_HORIZONTAL) {
            canvas.translate(mTransilation, 0);
        }else {
            canvas.translate(0, mTransilation);
        }
    }

    @Override
    protected void increace() {
        super.increace();
        if (mStepIndex < -DEFAULT_MAX_SETP) {
            mStepIndex ++;
            mStepFlag = 2;
        }else if (mStepIndex > DEFAULT_MAX_SETP) {
            mStepIndex --;
            mStepFlag = -2;
        }
        mTransilation = mDelta * mStepIndex;
        mStepIndex = mStepIndex + mStepFlag * mDelta;
    }
}
