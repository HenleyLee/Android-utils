package com.henley.android.utils;

import android.os.Build;

/**
 * SDK版本工具类
 *
 * @author liyunlong
 * @since 2020/5/26 15:45
 */
public final class SDKHelper {

    private SDKHelper() {
        throw new UnsupportedOperationException("Instantiation operation is not supported.");
    }

    /**
     * 判断SDK版本是否大于Android 4.4(API level 19,KitKat)
     */
    public static boolean isAtLeastK() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 判断SDK版本是否大于Android 5.0(API level 21,Lollipop)
     */
    public static boolean isAtLeastL() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 判断SDK版本是否大于Android 6.0(API level 23,Marshmallow)
     */
    public static boolean isAtLeastM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 判断SDK版本是否大于Android 7.0(API level 24,Nougat)
     */
    public static boolean isAtLeastN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * 判断SDK版本是否大于Android 8.0(Oreo,API level 26)
     */
    public static boolean isAtLeastO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * 判断SDK版本是否大于Android 9.0(API level 28,Pixel)
     */
    public static boolean isAtLeastP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * 判断SDK版本是否大于Android 10(API level 29)
     */
    public static boolean isAtLeastQ() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

}
