package com.kidosc.pushlibrary.cache;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author yolo.huang
 * @date 2018/12/27
 */

public class PushTokenCache {

    private static final String FILE_PUSH_LIBRARY_CACHE = "push_library_cache";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_PLATFORM = "platform";

    public static void putToken(Context context, String token) {
        getSharedPreferences(context).edit().putString(KEY_TOKEN, token).apply();
    }

    public static String getToken(Context context) {
        return getSharedPreferences(context).getString(KEY_TOKEN, null);
    }

    public static void delToken(Context context) {
        getSharedPreferences(context).edit().remove(KEY_TOKEN).apply();
    }

    public static void putPlatform(Context context, String platform) {
        getSharedPreferences(context).edit().putString(KEY_PLATFORM, platform).apply();
    }

    public static String getPlatform(Context context) {
        return getSharedPreferences(context).getString(KEY_PLATFORM, null);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(FILE_PUSH_LIBRARY_CACHE, Context.MODE_PRIVATE);
    }

    public static void delPlatform(Context context) {
        getSharedPreferences(context).edit().remove(KEY_PLATFORM).apply();
    }
}
