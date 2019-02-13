package com.kidosc.pushlibrary.util;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.kidosc.pushlibrary.model.PushTargetEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 系统标识
 * @author yolo.huang
 */

public class RomUtils {


    private static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            PushLogUtil.e( "Unable to read sysprop " + propName, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    PushLogUtil.e( "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }


    /**
     * 判断是否为华为UI
     */
    public static boolean isHuaweiRom() {
        String manufacturer = Build.MANUFACTURER;
        return !TextUtils.isEmpty(manufacturer) && manufacturer.contains("HUAWEI");
    }

    /**
     * 判断是否为小米UI
     */
    public static boolean isMiuiRom() {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
    }


    public static boolean isOPPORom() {
        return !TextUtils.isEmpty(getSystemProperty("ro.build.version.opporom"));
    }

    public static boolean isVivoRom() {
        return !TextUtils.isEmpty(getSystemProperty("ro.vivo.os.version"));
    }

}
