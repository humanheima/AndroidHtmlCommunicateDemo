package com.brotherd.androidhtmlcommunicatedemo.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by dumingwei on 2017/5/9.
 */
public class ScreenUtil {

    private static final String TAG = "ScreenUtil";

    public static int dpToPx(Activity context, int dp) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (int) (dp * displayMetrics.density);
    }
}
