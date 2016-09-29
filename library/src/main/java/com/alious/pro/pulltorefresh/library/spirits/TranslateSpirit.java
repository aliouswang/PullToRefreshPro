package com.alious.pro.pulltorefresh.library.spirits;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by aliouswang on 16/9/28.
 */

public class TranslateSpirit extends Spirit{

    public static final int TRANSLATE_HORIZONTAL = 0x01;
    public static final int TRANSLATE_VERTICAL = 0x02;

    private int mTranslateType = TRANSLATE_HORIZONTAL;

    protected float mTranslate = 0;
    protected float mTranslateDelta = 1.0f;

    protected float mStartTranslate = 0;
    protected float mEndTranslate = 0;

    protected boolean bIncrease = false;

    public TranslateSpirit(Context context, RectF rect, int resId) {
        super(context, rect, resId);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        if (mTranslateType == TRANSLATE_HORIZONTAL) {
            canvas.translate(mTranslate, 0);
        }else if (mTranslateType == TRANSLATE_VERTICAL) {
            canvas.translate(0, mTranslate);
        }
        super.draw(canvas);
        canvas.restore();
        if (bIncrease) {
            if (mTranslate >= mEndTranslate) {
                mTranslate = mStartTranslate;
            }
            mTranslate += mTranslateDelta;
        }else {
            if (mTranslate <= mEndTranslate) {
                mTranslate = mStartTranslate;
            }
            mTranslate -= mTranslateDelta;
        }
    }

    public void setTranslate(float translate) {
        mTranslate = translate;
    }

    public void setStartTranslate(float startTranslate) {
        mStartTranslate = startTranslate;
        mTranslate = mStartTranslate;
    }

    public void setEndTranslate(float endTranslate) {
        mEndTranslate = endTranslate;
    }

    public void setIncrease(boolean bIncrease) {
        this.bIncrease = bIncrease;
    }

    public void setTranslateDelta(float translateDelta) {
        mTranslateDelta = translateDelta;
    }
}
