package com.henley.android.utils;

import android.os.Looper;

/**
 * 线程辅助类
 *
 * @author liyunlong
 * @since 2020/5/26 15:56
 */
public final class ThreadHelper {

    private ThreadHelper() {
        throw new UnsupportedOperationException("Instantiation operation is not supported.");
    }

    /**
     * 判断是否在主线程
     */
    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 判断是否在后台线程
     */
    public static boolean isOnBackgroundThread() {
        return !isOnMainThread();
    }

}
