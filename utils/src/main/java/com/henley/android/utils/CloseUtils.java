package com.henley.android.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 关闭工具类
 *
 * @author Henley
 * @since 2020/5/25 14:23
 */
public final class CloseUtils {

    private CloseUtils() {
        throw new UnsupportedOperationException("Instantiation operation is not supported.");
    }

    /**
     * 关闭{@link Closeable}
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null || closeables.length <= 0) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安静关闭{@link Closeable}
     */
    public static void closeIOQuietly(Closeable... closeables) {
        if (closeables == null || closeables.length <= 0) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                    // ignore
                }
            }
        }
    }

}
