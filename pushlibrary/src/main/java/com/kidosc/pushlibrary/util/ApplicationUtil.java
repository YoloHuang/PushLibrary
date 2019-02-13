package com.kidosc.pushlibrary.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

/**
 * @author yolo.huang
 * 系统工具类
 * Created by yolo.huang on 2018/5/28.
 */

public class ApplicationUtil {
    /**
     * 获取Manifest 里面的meta-data
     *
     * @param context
     * @param key
     * @return
     */
    public static String getMetaData(Context context, String key) {
        ApplicationInfo ai = null;
        try {
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        Bundle bundle = ai.metaData;
        String value = bundle.getString(key);
        return value;
    }

}
