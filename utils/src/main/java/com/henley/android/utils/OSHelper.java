package com.henley.android.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.DataOutputStream;
import java.io.File;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

/**
 * 系统设备信息工具类
 *
 * @author liyunlong
 * @since 2020/5/25 14:21
 */
public final class OSHelper {

    private OSHelper() {
        throw new UnsupportedOperationException("Instantiation operation is not supported.");
    }

    /**
     * 判断是否已ROOT
     */
    public static boolean isRoot() {
        try {
            return new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否已经ROOT
     */
    public static synchronized boolean isRootAuth() {
        DataOutputStream os = null;
        try {
            Process process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("exit\n");
            os.flush();
            int exitValue = process.waitFor();
            process.destroy();
            return exitValue == 0;
        } catch (Exception e) {
            return false;
        } finally {
            CloseHelper.closeIOQuietly(os);
        }
    }

    /**
     * 判断是否为模拟器
     */
    public static boolean isEmulator(Context context) {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT)
                || NetworkHelper.getSimOperatorName(context).toLowerCase().equals("android");
    }

    /**
     * 获取设备系统版本号
     */
    public static String getRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备系统定制商
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备主板
     */
    public static String getBoard() {
        return Build.BOARD;
    }

    /**
     * 获取设备显示屏参数
     */
    public static String getDisplay() {
        return Build.DISPLAY;
    }

    /**
     * 获取设备硬件识别码
     */
    public static String getFingerprint() {
        return Build.FINGERPRINT;
    }

    /**
     * 获取设备硬件名称
     */
    public static String getHardware() {
        return Build.HARDWARE;
    }

    /**
     * 获取设备硬件制造商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备版本(最终用户可见的名称)
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取设备产品名称
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取设备硬件序列号
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    public static String getSerial() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Build.getSerial();
        } else {
            //noinspection deprecation
            return Build.SERIAL;
        }
    }

    /**
     * 获取设备CPU指令集
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String[] getSupportedABIS() {
        return Build.SUPPORTED_ABIS;
    }

    /**
     * 获取设备无线电固件版本
     */
    public static String getRadio() {
        return Build.getRadioVersion();
    }

    /**
     * 获取设备描述Build的标签
     */
    public static String getTags() {
        return Build.TAGS;
    }

    /**
     * 获取设备SDK版本
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 判断是否为飞行模式
     */
    public static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
    }

    /**
     * 判断SIM卡是否可用
     */
    public static boolean isSimAvailable(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);// 取得相关系统服务
        if (telephonyManager == null) {
            return false;
        }
        int simState = telephonyManager.getSimState();
        return simState == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * 判断是否含有SIM卡
     */
    public static boolean isSimInserted(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);// 取得相关系统服务
        if (telephonyManager == null) {
            return false;
        }
        int simState = telephonyManager.getSimState();
        return simState != TelephonyManager.SIM_STATE_UNKNOWN && simState != TelephonyManager.SIM_STATE_ABSENT;
    }

}
