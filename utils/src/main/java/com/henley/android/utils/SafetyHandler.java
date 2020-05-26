package com.henley.android.utils;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;

/**
 * 安全的{@link Handler}(解决{@link Handler}导致的内存泄漏)
 *
 * @author liyunlong
 * @since 2020/5/25 18:03
 */
public final class SafetyHandler extends Handler {

    private final WeakReference<Delegate> mWeakReference;

    public static SafetyHandler create(@NonNull Delegate delegate) {
        return new SafetyHandler(delegate);
    }

    private SafetyHandler(@NonNull Delegate delegate) {
        this.mWeakReference = new WeakReference<>(delegate);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        Delegate delegate = getDelegate();
        if (delegate == null) {
            clear();
            return;
        }
        delegate.handleMessage(msg);
    }

    public Delegate getDelegate() {
        return mWeakReference.get();
    }

    /**
     * 清空当前{@link Handler}队列所有消息并移除{@link WeakReference}持有的{@link Delegate}引用
     */
    public void clear() {
        removeCallbacksAndMessages(null);
        mWeakReference.clear();
    }

    public interface Delegate {
        void handleMessage(@NonNull Message msg);
    }

}
