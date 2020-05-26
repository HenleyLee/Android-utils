package com.henley.android.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数值辅助类
 *
 * @author liyunlong
 * @since 2020/5/25 13:59
 */
public final class NumberHelper {

    private NumberHelper() {
        throw new UnsupportedOperationException("Instantiation operation is not supported.");
    }

    /**
     * 将字符串转换为Integer类型的数字
     */
    public static int parseInt(String number) {
        return parseInt(number, 0);
    }

    /**
     * 将字符串转换为Integer类型的数字
     */
    public static int parseInt(String number, int defaultValue) {
        int value;
        try {
            value = Integer.parseInt(number);
        } catch (NumberFormatException | NullPointerException e) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * 将字符串转换为Long类型的数字
     */
    public static long parseLong(String number) {
        return parseLong(number, 0L);
    }

    /**
     * 将字符串转换为Long类型的数字
     */
    public static long parseLong(String number, long defaultValue) {
        long value;
        try {
            value = Long.parseLong(number);
        } catch (NumberFormatException | NullPointerException e) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * 将字符串转换为Float类型的数字
     */
    public static float parseFloat(String number) {
        return parseFloat(number, 0f);
    }

    /**
     * 将字符串转换为Float类型的数字
     */
    public static float parseFloat(String number, float defaultValue) {
        float value;
        try {
            value = Float.parseFloat(number);
        } catch (NumberFormatException | NullPointerException e) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * 将字符串转换为Double类型的数字
     */
    public static double parseDouble(String number) {
        return parseDouble(number, 0L);
    }

    /**
     * 将字符串转换为Double类型的数字
     */
    public static double parseDouble(String number, double defaultValue) {
        double value;
        try {
            value = Double.parseDouble(number);
        } catch (NumberFormatException | NullPointerException e) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * 截取字符串中的数字
     */
    public static String getNumberFromString(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    /**
     * 返回字符串中的数字段
     */
    public static List<String> getNumberList(String content) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        Pattern compile = Pattern.compile("\\d+");
        Matcher matcher = compile.matcher(content);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    /**
     * @param format String
     * @return String 格式化投注金额
     */
    public static String formatMaxTwoDecimal(String format) {
        return formateDecimalFromString(format, "###,###,###,##0.##");
    }

    /**
     * @param format String
     * @return String 格式化投注金额
     */
    public static String formatTwoDecimal(String format) {
        return formateDecimalFromString(format, "###,###,###,##0.00");
    }


    public static String formateDecimalFromString(String value, String format) {
        if (TextUtils.isEmpty(value) || "0".equals(value)) return "0";
        double number = 0;
        try {
            number = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return formatDecimal(number, format);
    }


    /**
     * @param format String
     * @return String 格式化投注金额
     */
    public static String formatNoDecimal(String format) {
        return formateDecimalFromString(format, "###,###,###,###,##0");
    }


    /**
     * 保留一位小数
     */
    public static String formatOneDecimal(double number) {
        return formatDecimal(number, "##0.0");
    }

    /**
     * 保留两位小数
     */
    public static String formatTwoDecimal(double number) {
        return formatDecimal(number, "##0.00");
    }

    /**
     * 保留两位小数百分比
     */
    public static String formatTwoDecimalPercent(double number) {
        return formatDecimal(number, "##0.00%");
    }

    /**
     * 格式化数字
     */
    public static String formatDecimal(long number, String pattern) {
        return new DecimalFormat(pattern).format(number);
    }

    /**
     * 格式化数字
     */
    public static String formatDecimal(double number, String pattern) {
        return new DecimalFormat(pattern).format(number);
    }

    /**
     * 四舍五入
     */
    public static double roundingNumber(float number, int scale) {
        return roundingNumber(number, scale, RoundingMode.HALF_UP);
    }

    /**
     * 四舍五入
     * <br/>{@link RoundingMode#HALF_UP} 最近数字舍入(5进)
     * <br/>{@link RoundingMode#HALF_DOWN} 最近数字舍入(5舍)
     * <br/>{@link RoundingMode#HALF_EVEN} 银行家舍入法
     */
    public static double roundingNumber(float number, int scale, RoundingMode roundingMode) {
        BigDecimal decimal = new BigDecimal(number);
        return decimal.setScale(scale, roundingMode).doubleValue();
    }

}
