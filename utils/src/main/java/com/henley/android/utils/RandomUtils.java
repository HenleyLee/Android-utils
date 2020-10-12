package com.henley.android.utils;

import android.text.TextUtils;

import java.util.Random;
import java.util.TreeSet;

/**
 * 随机数工具类(可用于获取固定长度固定字符内的随机数)
 *
 * @author Henley
 * @since 2020/5/26 15:39
 */
public final class RandomUtils {

    /**
     * 数字
     */
    public static final String NUMBERS = "0123456789";
    /**
     * 小写字母
     */
    public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    /**
     * 大写字母
     */
    public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 大小写字母
     */
    public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 数字和大小写字母
     */
    public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private RandomUtils() {
        throw new UnsupportedOperationException("Instantiation operation is not supported.");
    }

    /**
     * 得到一个指定长度length的随机字符串(由数字和大小写字母组成)
     *
     * @param length 随机字符串长度
     * @return {@link RandomUtils#getRandom(String source, int length)}
     */
    public static String getRandomNumbersAndLetters(int length) {
        return getRandom(NUMBERS_AND_LETTERS, length);
    }

    /**
     * 得到一个指定长度length的随机字符串(由数字组成)
     *
     * @param length 随机字符串长度
     * @return {@link RandomUtils#getRandom(String source, int length)}
     */
    public static String getRandomNumbers(int length) {
        return getRandom(NUMBERS, length);
    }

    /**
     * 得到一个指定长度length的随机字符串(由大小写字母组成)
     *
     * @param length 随机字符串长度
     * @return {@link RandomUtils#getRandom(String source, int length)}
     */
    public static String getRandomLetters(int length) {
        return getRandom(LETTERS, length);
    }

    /**
     * 得到一个指定长度length的随机字符串(由大写字母组成)
     *
     * @param length 随机字符串长度
     * @return {@link RandomUtils#getRandom(String source, int length)}
     */
    public static String getRandomCapitalLetters(int length) {
        return getRandom(CAPITAL_LETTERS, length);
    }

    /**
     * 得到一个指定长度length的随机字符串(由小写字母组成)
     *
     * @param length 随机字符串长度
     * @return {@link RandomUtils#getRandom(String source, int length)}
     */
    public static String getRandomLowerCaseLetters(int length) {
        return getRandom(LOWER_CASE_LETTERS, length);
    }

    /**
     * 得到一个指定长度length的随机字符串(由指定字符串source中的字符组成)
     *
     * @param source 指定字符串
     * @param length 随机字符串长度
     * @return 如果source为空, 则返回null;反之返回{@link RandomUtils#getRandom(char[] sourceChar, int length)}
     */
    public static String getRandom(String source, int length) {
        return TextUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
    }

    /**
     * 得到一个指定长度length的随机字符串(由指定字符数组sourceChar中的字符组成)
     *
     * @param sourceChar 指定字符数组
     * @param length     随机字符串长度
     * @return 如果sourceChar为空, 则返回null;如果length小于等于0，则返回null;反之返回一个指定长度的随机字符串
     */
    public static String getRandom(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length <= 0) {
            return null;
        }
        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }

    /**
     * 得到一个0~max之间的随机数
     *
     * @param max 最大值
     * @return 如果max小于等于0, 则返回0;反之则返回一个0到max之间的随机数
     */
    public static int getRandom(int max) {
        return getRandom(0, max);
    }

    /**
     * 得到一个  min~max 之间的随机数
     *
     * @param min
     * @param max
     * @return 如果min>max，则返回0；如果min=max，则返回min；反之则返回一个min~max之间的随机数
     */
    public static int getRandom(int min, int max) {
        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }

    /**
     * 得到{@code size}个  min~max 之间的随机数
     *
     * @param min
     * @param max
     * @param size
     */
    public static TreeSet<Integer> getSomeRandom(int min, int max, int size) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        while (treeSet.size() < size) {
            treeSet.add(getRandom(min, max));
        }
        return treeSet;
    }

    /**
     * 将指定数组随机排列(将指定Object[]中的元素随机交换随机次)
     *
     * @param objArray 指定数组
     */
    public static boolean shuffle(Object[] objArray) {
        if (objArray == null) {
            return false;
        }
        return shuffle(objArray, getRandom(objArray.length));
    }

    /**
     * 将指定数组随机排列(将指定Object[]中的元素随机交换shuffleCount次)
     *
     * @param objArray     指定数组
     * @param shuffleCount 随机交换次数
     */
    public static boolean shuffle(Object[] objArray, int shuffleCount) {
        int length;
        if (objArray == null || shuffleCount < 0
                || (length = objArray.length) < shuffleCount) {
            return false;
        }
        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            Object temp = objArray[length - i];
            objArray[length - i] = objArray[random];
            objArray[random] = temp;
        }
        return true;
    }

    /**
     * 从指定int[]中得到一个长度随机的随机int[]
     *
     * @param intArray 指定数组
     */
    public static int[] shuffle(int[] intArray) {
        if (intArray == null) {
            return null;
        }
        return shuffle(intArray, getRandom(intArray.length));
    }

    /**
     * 从指定int[]中得到一个指定长度为shuffleCount的随机int[]
     *
     * @param intArray     指定数组
     * @param shuffleCount 指定长度
     */
    public static int[] shuffle(int[] intArray, int shuffleCount) {
        int length;
        if (intArray == null || shuffleCount < 0
                || (length = intArray.length) < shuffleCount) {
            return null;
        }
        int[] out = new int[shuffleCount];
        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            out[i - 1] = intArray[random];
            int temp = intArray[length - i];
            intArray[length - i] = intArray[random];
            intArray[random] = temp;
        }
        return out;
    }

}
