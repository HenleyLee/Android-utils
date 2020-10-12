package com.henley.android.utils;

import java.util.LinkedList;

/**
 * 防抖动点击辅助类
 *
 * @author Henley
 * @since 2020/5/26 14:19
 */
public final class AntiShake {

    private static final int DEFAULT_TRACE_INDEX = 3;
    private static final int DEFAULT_LIMITED_SIZE = 20;
    private static final long CLICK_TIME_INTERVAL = 800L; // 按钮有效点击时间间隔

    private static LimitQueue<OneClick> queue = new LimitQueue<>(DEFAULT_LIMITED_SIZE);

    public static boolean check() {
        return check(CLICK_TIME_INTERVAL);
    }

    public static boolean check(long interval) {
        return check(null, interval);
    }

    public static boolean check(Object o) {
        return check(o, CLICK_TIME_INTERVAL);
    }

    public static boolean check(Object o, long interval) {
        String flag;
        if (o != null) {
            flag = o.toString();
        } else {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            int index = DEFAULT_TRACE_INDEX;
            flag = elements[index].getMethodName();
            while (AntiShake.class.getName().equals(elements[index].getClassName())) {
                index++;
                flag = elements[index].getMethodName();
            }
        }
        for (OneClick click : queue) {
            if (click.getFlag().equals(flag)) {
                return click.check();
            }
        }
        OneClick click = new OneClick(flag, interval);
        queue.offer(click);
        return click.check();
    }

    private static final class LimitQueue<E> extends LinkedList<E> {

        private int limitedSize;

        private LimitQueue(int size) {
            this.limitedSize = size;
        }

        @Override
        public boolean offer(E e) {
            if (size() >= limitedSize) {
                poll();
            }
            return super.offer(e);
        }
    }

    private static final class OneClick {

        private String flag;
        private long interval;
        private long lastClickTime = 0;

        private OneClick(String flag, long interval) {
            this.flag = flag;
            this.interval = interval;
        }

        private String getFlag() {
            return flag;
        }

        private boolean check() {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > interval) {
                lastClickTime = currentTime;
                return false;
            }
            return true;
        }

    }

}
