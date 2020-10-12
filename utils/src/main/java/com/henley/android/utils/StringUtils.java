package com.henley.android.utils;

import android.os.Build;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

/**
 * 字符串工具类
 *
 * @author Henley
 * @since 2020/5/25 14:30
 */
public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("Instantiation operation is not supported.");
    }

    /**
     * 获取字符串
     */
    public static String getString(@StringRes int resId) {
        return Tool.getContext().getString(resId);
    }

    /**
     * 判断字符串是否为空(字符串为null或长度为0或由空格组成)
     *
     * @return 如果字符串为null或长度为0或由空格组成，则返回true；反之返回false
     */
    public static boolean isBlank(CharSequence str) {
        return (str == null || str.toString().trim().length() == 0);
    }

    /**
     * 判断字符串是否为空(字符串为null或长度为0)
     *
     * @param str
     * @return 如果字符串为null或长度为0，则返回true；反之返回false
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * 比较两个字符串是否相等
     */
    public static boolean equals(CharSequence actual, CharSequence expected) {
        if (actual == expected) return true;
        int length;
        if (actual != null && expected != null && (length = actual.length()) == expected.length()) {
            if (actual instanceof String && expected instanceof String) {
                return actual.equals(expected);
            } else {
                for (int i = 0; i < length; i++) {
                    if (actual.charAt(i) != expected.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 获取字符串长度
     *
     * @param str
     * @return 如果字符串为空，则返回0；反之返回{@link CharSequence#length()}.
     */
    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 将object转换为字符串
     *
     * @return 如果object为null，则返回""；如果object是String类型，则返回object；反之返回{@link Object#toString()}
     */
    public static String objectToString(Object object) {
        return (object == null ? "" : (object instanceof String ? (String) object : object.toString()));
    }

    /**
     * 将字符串的首字母大写
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char charAt = str.charAt(0);
        return (!Character.isLetter(charAt) || Character.isUpperCase(charAt)) ? str :
                Character.toUpperCase(charAt) + str.substring(1);
    }

    /**
     * 将字符串进行UTF-8编码
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * 将字符串进行UTF-8编码，如果发生异常则返回defultReturn
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    /**
     * 返回非空字符串
     *
     * @param original 原始字符串
     */
    @NonNull
    public static String getNotNullString(@Nullable String original) {
        return getNotNullString(original, "");
    }

    /**
     * 返回非空字符串
     *
     * @param original 原始字符串
     */
    public static String getNotNullString(@Nullable String original, String defValue) {
        if (TextUtils.isEmpty(original)) {
            original = defValue;
        }
        return original;
    }

    /**
     * 格式化字符串
     */
    public static String format(@StringRes int resId, Object... args) {
        return format(getString(resId), args);
    }

    /**
     * 格式化字符串
     */
    public static String format(String format, Object... args) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return String.format(Locale.getDefault(Locale.Category.FORMAT), format, args);
        } else {
            return String.format(Locale.getDefault(), format, args);
        }
    }

}
