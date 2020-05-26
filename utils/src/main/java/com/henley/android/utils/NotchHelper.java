package com.henley.android.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import androidx.annotation.NonNull;

/**
 * 刘海屏辅助类
 *
 * @author liyunlong
 * @since 2020/5/26 15:31
 */
public final class NotchHelper {

    private static final String TAG = "NotchHelper";
    /**
     * 是否有凹槽
     */
    public static final int VOIO_NOTCH_IN_SCREEN = 0x00000020;
    /**
     * 是否有圆角
     */
    public static final int VOIO_ROUNDED_IN_SCREEN = 0x00000008;
    /**
     * 刘海屏全屏显示
     */
    public static final int HUAWEI_FLAG_NOTCH_SUPPORT = 0x00010000;
    private static final String KEY_MIUI_NOTCH = "ro.miui.notch";


    //<editor-fold desc="官方API 处理刘海屏信息">

    /**
     * 设置{@link Activity}的窗口布局属性
     * <ul>
     * <strong>窗口布局属性：</strong>
     * <li>{@link WindowManager.LayoutParams#LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT}：默认情况下，只有当DisplayCutout完全包含在系统栏中时，才允许窗口延伸到DisplayCutout区域。 否则，窗口布局不与DisplayCutout区域重叠。
     * <li>{@link WindowManager.LayoutParams#LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES}：窗口始终允许延伸到屏幕短边上的DisplayCutout区域。
     * <li>{@link WindowManager.LayoutParams#LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER}：窗口决不允许与DisplayCutout区域重叠。
     * </ul>
     *
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.P)
    public static void setDisplayCutoutMode(Activity activity, int mode) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.layoutInDisplayCutoutMode = mode;
        window.setAttributes(params);
    }

    /**
     * 处理刘海屏信息
     */
    public static void handleNotchScreen(final Activity activity) {
        boolean hasNotch = NotchHelper.hasNotchInScreen(activity);
        int notchHeight = NotchHelper.getNotchHeight(activity);
        if (!hasNotch && notchHeight > 0) {
            hasNotch = true;
        }
        if (hasNotch && notchHeight == 0) {
            notchHeight = ScreenHelper.getStatusBarHeight(activity);
        }
        NotchScreen notchScreen = new NotchScreen();
        notchScreen.setHasNotch(hasNotch);
        notchScreen.setNotchHeight(notchHeight);
        Log.i(TAG, notchScreen.toString());
    }

    //</editor-fold>

    //<editor-fold desc="核心方法">

    private static boolean hasNotchInScreen(Activity activity) {
        boolean hasNotch = false;
        int systemType = SystemHelper.getSystemType();
        if (systemType == SystemType.SYSTEM_EMUI) {             // 华为
            hasNotch = hasNotchInScreenForHuawei(activity);
        } else if (systemType == SystemType.SYSTEM_MIUI) {      // 小米
            hasNotch = hasNotchInScreenForXiaomi(activity);
        } else if (systemType == SystemType.SYSTEM_OPPO) {      // OPPO
            hasNotch = hasNotchInScreenForOppo(activity);
        } else if (systemType == SystemType.SYSTEM_VIVO) {      // VIVO
            hasNotch = hasNotchInScreenForVoio(activity);
        }
        return hasNotch;
    }

    @SuppressLint("SwitchIntDef")
    private static int getNotchHeight(Activity activity) {
        int notchHeight = 0;
        int systemType = SystemHelper.getSystemType();
        if (systemType == SystemType.SYSTEM_EMUI) {         // 华为
            notchHeight = getNotchSize(activity)[1];
        }
        if (notchHeight == 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            View decorView = activity.getWindow().getDecorView();
            notchHeight = getSafeInsetTop(decorView);
        }
        return notchHeight;
    }


    /**
     * 获取刘海屏的高度
     */
    @TargetApi(Build.VERSION_CODES.P)
    private static int getSafeInsetTop(View decorView) {
        try {
            WindowInsets windowInsets = decorView.getRootWindowInsets();
            if (windowInsets != null) {
                DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                if (displayCutout != null) {
                    return displayCutout.getSafeInsetTop();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //</editor-fold>

    //<editor-fold desc="华为">

    /**
     * 判断是否有刘海屏(华为)
     */
    @SuppressWarnings("unchecked")
    private static boolean hasNotchInScreenForHuawei(Context context) {
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class clazz = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method method = clazz.getMethod("hasNotchInScreen");
            return (boolean) method.invoke(clazz);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "hasNotchInScreen ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "hasNotchInScreen NoSuchMethodException");
        } catch (Exception e) {
            Log.e(TAG, "hasNotchInScreen Exception");
        }
        return false;
    }

    /**
     * 获取刘海尺寸信息
     *
     * @return [0]值为刘海宽度int;[1]值为刘海高度
     */
    @SuppressWarnings("unchecked")
    private static int[] getNotchSize(Context context) {
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class clazz = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method method = clazz.getMethod("getNotchSize");
            return (int[]) method.invoke(clazz);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "getNotchSize ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "getNotchSize NoSuchMethodException");
        } catch (Exception e) {
            Log.e(TAG, "getNotchSize Exception");
        }
        return new int[]{0, 0};
    }

    /**
     * 设置应用窗口在华为刘海屏手机使用挖孔区
     *
     * @param window 应用页面window对象
     */
    @SuppressWarnings("unchecked")
    private static void setFullScreenWindowLayoutInDisplayCutout(Window window) {
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        try {
            Class clazz = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor constructor = clazz.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj = constructor.newInstance(layoutParams);
            Method method = clazz.getMethod("addHwFlags", int.class);
            method.invoke(layoutParamsExObj, HUAWEI_FLAG_NOTCH_SUPPORT);
        } catch (Exception e) {
            Log.e(TAG, "hw notch screen flag api error");
        }
    }
    //</editor-fold>

    //<editor-fold desc="小米">

    /**
     * 判断是否有刘海屏(小米)
     */
    private static boolean hasNotchInScreenForXiaomi(Context context) {
        String property = SystemHelper.getSystemProperty(KEY_MIUI_NOTCH);
        return "1".equals(property); // 值为1时则是 Notch 屏手机
    }

    //</editor-fold>

    //<editor-fold desc="OPPO">

    /**
     * 判断是否有刘海屏(OPPO)
     */
    private static boolean hasNotchInScreenForOppo(Context context) {
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }
    //</editor-fold>

    //<editor-fold desc="VOIO">

    /**
     * 判断是否有刘海屏(VOIO)
     */
    @SuppressLint("PrivateApi")
    @SuppressWarnings("unchecked")
    private static boolean hasNotchInScreenForVoio(Context context) {
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class clazz = classLoader.loadClass("android.util.FtFeature");
            Method method = clazz.getMethod("isFeatureSupport", int.class);
            return (boolean) method.invoke(clazz, VOIO_NOTCH_IN_SCREEN);
        } catch (ClassNotFoundException e) {
            Log.e("test", "hasNotchInScreen ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "hasNotchInScreen NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "hasNotchInScreen Exception");
        }
        return false;
    }

    //</editor-fold>

    //<editor-fold desc="Notch屏幕信息">

    /**
     * Notch屏幕信息
     */
    public static class NotchScreen {

        private int notchHeight;            // 刘海高度
        private boolean hasNotch;           // 是否具有刘海

        public int getNotchHeight() {
            return notchHeight;
        }

        public void setNotchHeight(int notchHeight) {
            this.notchHeight = notchHeight;
        }

        public boolean isHasNotch() {
            return hasNotch;
        }

        public void setHasNotch(boolean hasNotch) {
            this.hasNotch = hasNotch;
        }

        @NonNull
        @Override
        public String toString() {
            return "NotchScreen{" +
                    "notchHeight=" + notchHeight +
                    ", hasNotch=" + hasNotch +
                    '}';
        }

    }

    //</editor-fold>

}
