package com.github.androiddevfr.form.core;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class DimensionUtils {
    public static float pxToDp(int px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static int dpToPx(int dp){
        return dpToPx((float) dp);
    }

    public static int dpToPx(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}
