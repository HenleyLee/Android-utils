package com.henley.android.utils;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * {@link Bundle}构建辅助类
 *
 * @author liyunlong
 * @since 2020/5/26 14:21
 */
public final class BundleHelper {

    private Bundle bundle;

    public static BundleHelper creat() {
        return new BundleHelper();
    }

    private BundleHelper() {
        bundle = new Bundle();
    }

    public BundleHelper putBoolean(@Nullable String key, boolean value) {
        bundle.putBoolean(key, value);
        return this;
    }

    public BundleHelper putBooleanArray(@Nullable String key, @Nullable boolean[] value) {
        bundle.putBooleanArray(key, value);
        return this;
    }

    public BundleHelper putByte(@Nullable String key, byte value) {
        bundle.putByte(key, value);
        return this;
    }

    public BundleHelper putByteArray(@Nullable String key, @Nullable byte[] value) {
        bundle.putByteArray(key, value);
        return this;
    }

    public BundleHelper putChar(@Nullable String key, char value) {
        bundle.putChar(key, value);
        return this;
    }

    public BundleHelper putCharArray(@Nullable String key, @Nullable char[] value) {
        bundle.putCharArray(key, value);
        return this;
    }

    public BundleHelper putShort(@Nullable String key, short value) {
        bundle.putShort(key, value);
        return this;
    }

    public BundleHelper putShortArray(@Nullable String key, @Nullable short[] value) {
        bundle.putShortArray(key, value);
        return this;
    }

    public BundleHelper putInt(@Nullable String key, int value) {
        bundle.putInt(key, value);
        return this;
    }

    public BundleHelper putIntArray(@Nullable String key, @Nullable int[] value) {
        bundle.putIntArray(key, value);
        return this;
    }

    public BundleHelper putLong(@Nullable String key, long value) {
        bundle.putLong(key, value);
        return this;
    }

    public BundleHelper putLongArray(@Nullable String key, @Nullable long[] value) {
        bundle.putLongArray(key, value);
        return this;
    }

    public BundleHelper putFloat(@Nullable String key, float value) {
        bundle.putFloat(key, value);
        return this;
    }

    public BundleHelper putFloatArray(@Nullable String key, float[] value) {
        bundle.putFloatArray(key, value);
        return this;
    }

    public BundleHelper putDouble(@Nullable String key, double value) {
        bundle.putDouble(key, value);
        return this;
    }

    public BundleHelper putDoubleArray(@Nullable String key, @Nullable double[] value) {
        bundle.putDoubleArray(key, value);
        return this;
    }

    public BundleHelper putString(@Nullable String key, String value) {
        bundle.putString(key, value);
        return this;
    }

    public BundleHelper putStringArray(@Nullable String key, @Nullable String[] value) {
        bundle.putStringArray(key, value);
        return this;
    }

    public BundleHelper putCharSequence(@Nullable String key, CharSequence value) {
        bundle.putCharSequence(key, value);
        return this;
    }

    public BundleHelper putCharSequenceArray(@Nullable String key, @Nullable CharSequence[] value) {
        bundle.putCharSequenceArray(key, value);
        return this;
    }

    public BundleHelper putSerializable(@Nullable String key, @Nullable Serializable value) {
        bundle.putSerializable(key, value);
        return this;
    }

    public BundleHelper putParcelable(@Nullable String key, @Nullable Parcelable value) {
        bundle.putParcelable(key, value);
        return this;
    }

    public BundleHelper putParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        bundle.putParcelableArray(key, value);
        return this;
    }

    public BundleHelper putBundle(@Nullable String key, @Nullable Bundle value) {
        bundle.putBundle(key, value);
        return this;
    }

    public BundleHelper putIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        bundle.putIntegerArrayList(key, value);
        return this;
    }

    public BundleHelper putStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        bundle.putStringArrayList(key, value);
        return this;
    }

    public BundleHelper putCharSequenceArrayList(@Nullable String key, @Nullable ArrayList<CharSequence> value) {
        bundle.putCharSequenceArrayList(key, value);
        return this;
    }

    public BundleHelper putParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends Parcelable> value) {
        bundle.putParcelableArrayList(key, value);
        return this;
    }

    public BundleHelper putSparseParcelableArray(@Nullable String key, @Nullable SparseArray<? extends Parcelable> value) {
        bundle.putSparseParcelableArray(key, value);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public BundleHelper putBinder(@Nullable String key, IBinder value) {
        bundle.putBinder(key, value);
        return this;
    }

    public BundleHelper putAll(Bundle bundle) {
        this.bundle.putAll(bundle);
        return this;
    }

    public BundleHelper remove(String key) {
        this.bundle.remove(key);
        return this;
    }

    /**
     * 返回{@link Bundle}对象
     */
    public Bundle get() {
        return bundle;
    }

    /**
     * 返回{@link Bundle}对象
     */
    public Bundle end() {
        return get();
    }

}
