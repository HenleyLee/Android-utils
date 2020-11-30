package com.henley.android.utils;

import android.os.Build;

/**
 * Android构建版本工具类
 *
 * @author Henley
 * @since 2020/5/26 15:45
 */
public final class BuildUtils {

    private BuildUtils() {
        throw new UnsupportedOperationException("Instantiation operation is not supported.");
    }

    /**
     * 返回当前设备的SDK版本
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 判断SDK版本是否大于Android 4.0(API level 14,Ice Cream Sandwich)
     */
    public static boolean isAtLeastI() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * 判断SDK版本是否大于Android 4.1(API level 16,Jelly Bean)
     */
    public static boolean isAtLeastJ() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
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

    /**
     * 判断SDK版本是否大于Android 11(API level 30)
     */
    public static boolean isAtLeastR() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

}
