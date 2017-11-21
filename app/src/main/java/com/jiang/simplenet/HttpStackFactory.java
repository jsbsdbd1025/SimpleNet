package com.jiang.simplenet;

import android.os.Build;

/**
 * Created by knowing on 2017/11/21.
 */

public class HttpStackFactory {

    private static final int GINGERBREAD_SDK_NUM = 9;

    public static HttpStack createHttpStack() {
        if (Build.VERSION.SDK_INT >= GINGERBREAD_SDK_NUM) {
            return new HttpUrlConnStack();
        }

        return null;
    }
}
