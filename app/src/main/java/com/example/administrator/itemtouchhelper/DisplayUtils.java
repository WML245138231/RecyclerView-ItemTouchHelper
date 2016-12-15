package com.example.administrator.itemtouchhelper;

import android.content.Context;

/**
 * Created by Administrator on 2016/12/15.
 */

public  class DisplayUtils {

    private DisplayUtils() {
    }

    private static float scale = 0.0f;

    public static void init(Context context) {
        scale = context.getResources().getDisplayMetrics().density;
    }

    public static int dpToPx(float dipValue) {
        return (int) (dipValue * scale + 0.5);
    }

    public static float pxToDp(float pxValue) {
        return pxValue / scale;
    }
}
