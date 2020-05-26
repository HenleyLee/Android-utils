package com.henley.android.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.LinkedList;
import java.util.List;

/**
 * 监听软键盘打开收起事件的辅助类
 * <ol>
 * <strong>通常软键盘的收起方式大致分为4种：</strong>
 * <li>点击软键盘右下角的Return按钮(系统收起)
 * <li>输入框焦点时按返回按钮(系统收起)
 * <li>点击软键盘和输入框的外部(自发收起)
 * <li>点击软键盘自带的收起按钮(软键盘收起)
 * </ol>
 *
 * @author liyunlong
 * @since 2020/5/26 15:54
 */
public final class SoftKeyboardStateChangedHelper implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final int DEFAULT_KEYBOARD_HEIGHT = 200;
    private View activityRootView;
    private int keyboardHeight;
    private boolean isSoftKeyboardOpened;
    private int defaultKeyboardHeight;
    private List<SoftKeyboardStateChangedListener> listeners = new LinkedList<>();

    public SoftKeyboardStateChangedHelper(Activity activity) {
        this(activity.getWindow().getDecorView().findViewById(android.R.id.content), false);
    }

    public SoftKeyboardStateChangedHelper(View activityRootView) {
        this(activityRootView, false);
    }

    public SoftKeyboardStateChangedHelper(View activityRootView, boolean isSoftKeyboardOpened) {
        this.activityRootView = activityRootView;
        this.isSoftKeyboardOpened = isSoftKeyboardOpened;
        this.activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        int heightPixels = Resources.getSystem().getDisplayMetrics().heightPixels;
        this.defaultKeyboardHeight = Math.min(heightPixels / 3, DEFAULT_KEYBOARD_HEIGHT);
    }

    @Override
    public void onGlobalLayout() {
        final Rect rect = new Rect();// 自己的视图仍然可见的部分填充的矩形区域
        activityRootView.getWindowVisibleDisplayFrame(rect);
        final int heightDiff = activityRootView.getRootView().getHeight() - (rect.bottom - rect.top);
        if (!isSoftKeyboardOpened && heightDiff > defaultKeyboardHeight) { //  如果超过默认键盘高度，则认为是键盘
            keyboardHeight = heightDiff;
            setSoftKeyboardOpened(true);
            notifyOnSoftKeyboardOpened();
        } else if (isSoftKeyboardOpened && heightDiff < defaultKeyboardHeight) {
            setSoftKeyboardOpened(false);
            notifyOnSoftKeyboardClosed();
        }
    }

    /**
     * 设置软键盘是否打开状态
     *
     * @param isSoftKeyboardOpened 软键盘是否打开状态
     */
    public void setSoftKeyboardOpened(boolean isSoftKeyboardOpened) {
        this.isSoftKeyboardOpened = isSoftKeyboardOpened;
    }

    /**
     * 返回软键盘是否打开状态
     */
    public boolean isSoftKeyboardOpened() {
        return isSoftKeyboardOpened;
    }

    /**
     * 返回软键盘的高度(默认为0，单位：px)
     */
    public int getKeyboardHeight() {
        return keyboardHeight;
    }

    /**
     * 添加软键盘打开收起事件的监听器
     *
     * @param listener 软键盘打开收起事件的监听器
     */
    public void addSoftKeyboardStateListener(SoftKeyboardStateChangedListener listener) {
        listeners.add(listener);
    }

    /**
     * 移除软键盘打开收起事件的监听器
     *
     * @param listener 软键盘打开收起事件的监听器
     */
    public void removeSoftKeyboardStateListener(SoftKeyboardStateChangedListener listener) {
        listeners.remove(listener);
    }

    /**
     * 移除所有的监听器
     */
    @SuppressLint("ObsoleteSdkInt")
    public void removeAllListeners() {
        if (!listeners.isEmpty()) {
            listeners.clear();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            //noinspection deprecation
            activityRootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    /**
     * 通知所有的监听器软键盘打开
     */
    private void notifyOnSoftKeyboardOpened() {
        for (SoftKeyboardStateChangedListener listener : listeners) {
            if (listener != null) {
                listener.onSoftKeyboardOpened(keyboardHeight);
            }
        }
    }

    /**
     * 通知所有的监听器软键盘收起
     */
    private void notifyOnSoftKeyboardClosed() {
        for (SoftKeyboardStateChangedListener listener : listeners) {
            if (listener != null) {
                listener.onSoftKeyboardClosed();
            }
        }
    }

    /**
     * 软键盘状态改变监听
     */
    public interface SoftKeyboardStateChangedListener {

        /**
         * 当软键盘打开时调用该方法
         *
         * @param keyboardHeight 软键盘高度
         */
        void onSoftKeyboardOpened(int keyboardHeight);

        /**
         * 当软键盘收起时调用该方法
         */
        void onSoftKeyboardClosed();
    }

}
