package com.henley.android.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * 设备系统辅助类
 *
 * @author liyunlong
 * @since 2020/5/26 15:31
 */
public final class SystemHelper {

    private static final String MANUFACTURER_XIAOMI = "Xiaomi";
    private static final String MANUFACTURER_HUAWEI = "huawei";
    private static final String MANUFACTURER_FLYME = "flyme";
    private static final String MANUFACTURER_OPPO = "OPPO";
    private static final String MANUFACTURER_VIVO = "VIVO";

    private static final String KEY_MIUI_VERSION = "ro.build.version.miui";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_SYSTEM_VERSION = "ro.confg.hw_systemversion";

    private static final String KEY_VERSION_OPPO = "ro.build.version.opporom";

    private static final String KEY_VERSION_VIVO = "ro.vivo.os.version";

    private static final String KEY_VERSION_SMARTISAN = "ro.smartisan.version";

    private static final String KEY_VERSION_GIONEE = "ro.gn.sv.version";

    private static final String KEY_VERSION_LENOVO = "ro.lenovo.lvp.version";

    /**
     * 返回设备系统类型
     */
    @SystemType
    public static int getSystemType() {
        if (isEMUI()) {
            return SystemType.SYSTEM_EMUI; // 华为
        } else if (isMIUI()) {
            return SystemType.SYSTEM_MIUI; // 小米
        } else if (isMeizu()) {
            return SystemType.SYSTEM_FLYME; // 魅族
        } else if (isOPPO()) {
            return SystemType.SYSTEM_OPPO; // OPPO
        } else if (isVIVO()) {
            return SystemType.SYSTEM_VIVO; // VIVO
        } else if (isSmartisan()) {
            return SystemType.SYSTEM_SMARTISAN; // 锤子
        } else if (isGionee()) {
            return SystemType.SYSTEM_GIONEE; // 金立
        } else if (isLenovo()) {
            return SystemType.SYSTEM_LENOVO; // 联想
        } else if (is360()) {
            return SystemType.SYSTEM_360; // 360
        } else {
            return SystemType.SYSTEM_UNKNOWN; // 其它
        }
    }

    public static boolean isMIUI() {
        if (MANUFACTURER_XIAOMI.equalsIgnoreCase(Build.MANUFACTURER)) {
            return true;
        }
        String version = getSystemProperty(KEY_MIUI_VERSION);
        if (!TextUtils.isEmpty(version)) {
            return true;
        }
        return hasProperty(KEY_MIUI_VERSION_CODE, KEY_MIUI_VERSION_NAME, KEY_MIUI_INTERNAL_STORAGE);
    }

    public static boolean isEMUI() {
        if (MANUFACTURER_HUAWEI.equalsIgnoreCase(Build.MANUFACTURER)) {
            return true;
        }
        String version = getSystemProperty(KEY_EMUI_VERSION);
        if (!TextUtils.isEmpty(version)) {
            return true;
        }
        return hasProperty(KEY_EMUI_API_LEVEL, KEY_EMUI_VERSION, KEY_EMUI_CONFIG_SYSTEM_VERSION);
    }

    public static boolean isMeizu() {
        if (Build.DISPLAY.contains("FLYME")) {
            return true;
        }
        if ("meizu".equalsIgnoreCase(Build.BRAND)) {
            return true;
        }
        String display = getSystemProperty("ro.build.display.id");
        if (!TextUtils.isEmpty(display) && display.toLowerCase().contains(MANUFACTURER_FLYME)) {
            return true;
        }
        String model = getSystemProperty("ro.meizu.product.model");
        if (!TextUtils.isEmpty(model)) {
            return true;
        }
        return false;
    }

    public static boolean isOPPO() {
        if (MANUFACTURER_OPPO.equalsIgnoreCase(Build.MANUFACTURER)) {
            return true;
        }
        String version = getSystemProperty(KEY_VERSION_OPPO);
        if (!TextUtils.isEmpty(version)) {
            return true;
        }
        return false;
    }

    public static boolean isVIVO() {
        if (MANUFACTURER_VIVO.equalsIgnoreCase(Build.MANUFACTURER)) {
            return true;
        }
        String version = getSystemProperty(KEY_VERSION_VIVO);
        if (!TextUtils.isEmpty(version)) {
            return true;
        }
        return false;
    }

    public static boolean isSmartisan() {
        String version = getSystemProperty(KEY_VERSION_SMARTISAN);
        if (!TextUtils.isEmpty(version)) {
            return true;
        }
        return false;
    }

    public static boolean isGionee() {
        String version = getSystemProperty(KEY_VERSION_GIONEE);
        if (!TextUtils.isEmpty(version)) {
            return true;
        }
        return false;
    }

    public static boolean isLenovo() {
        String version = getSystemProperty(KEY_VERSION_LENOVO);
        if (!TextUtils.isEmpty(version)) {
            return true;
        }
        return false;
    }

    public static boolean is360() {
        if ("360".equalsIgnoreCase(Build.MANUFACTURER) || "QIKU".equalsIgnoreCase(Build.MANUFACTURER)) {
            return true;
        }
        return false;
    }

    private static boolean hasProperty(String... propertys) {
        boolean hasProperty = false;
        if (propertys != null && propertys.length > 0) {
            for (String property : propertys) {
                if (!TextUtils.isEmpty(getSystemProperty(property))) {
                    hasProperty = true;
                    break;
                }
            }
        }
        return hasProperty;
    }

    public static String getSystemProperty(String key) {
        try {
            @SuppressLint("PrivateApi")
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, null);
        } catch (Exception e) {
        }
        return null;
    }

    public static String getProperty(String name) {
        String line = null;
        BufferedReader input = null;
        try {
            Process process = Runtime.getRuntime().exec("getprop " + name);
            input = new BufferedReader(new InputStreamReader(process.getInputStream()), 1024);
            line = input.readLine();
        } catch (IOException e) {
            Log.e("TAG", "Unable to read prop " + name, e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

}
