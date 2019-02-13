package com.kidosc.pushlibrary.util;

import android.util.Log;

/**
 * @author yolo.huang
 * @date 2019/1/3
 */

public class PushLogUtil {

    private static boolean sDebug = false;

    public static final String TAG = "PushLogUtil";

    public static void i(String log) {
        if (sDebug) {
            Log.i(TAG, log);
        }
    }

    public static void d(String log) {
        if (sDebug) {
            Log.d(TAG, log);
        }
    }

    public static void e(String log) {
        if (sDebug) {
            Log.e(TAG, log);
        }
    }

    public static void e(String log, Throwable throwable) {
        if (sDebug) {
            Log.e(TAG, log, throwable);
        }
    }

    public static void setDebug(boolean isDebug) {
        sDebug = isDebug;
    }

    public static boolean isDebug() {
        return sDebug;
    }


}
