package com.alious.pro.pulltorefresh.library;

import android.content.Context;

/**
 * Created by aliouswang on 16/9/12.
 */
public class Utils {

    /**
     * dip to dp
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dipToPx(Context context, float dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }


}
