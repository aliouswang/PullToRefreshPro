package com.alious.pro.pulltorefresh.library.spirits;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Rotate spirit
 *
 * Created by aliouswang on 16/9/28.
 */

public class RotateSpirit extends Spirit{

    public static final int TYPE_AUTO_ROTATE = 0x01;
    public static final int TYPE_PERCENTAGE = 0x02;

    public static final int DEFAULT_ANGEL_DELTA = 10;

    private int mRotateAngel;
    private int mRotateType = TYPE_AUTO_ROTATE;

    private int mAngelDelta = DEFAULT_ANGEL_DELTA;

    public RotateSpirit(Context context, RectF rect, int resId) {
        super(context, rect, resId);
    }

    public void setAngelDelta(int angelDelta) {
        mAngelDelta = angelDelta;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(mRotateAngel, mDrawRect.centerX(), mDrawRect.centerY());
        super.draw(canvas);
        canvas.restore();
        if (mRotateType == TYPE_AUTO_ROTATE) {
            mRotateAngel += mAngelDelta;
        }
    }
}
