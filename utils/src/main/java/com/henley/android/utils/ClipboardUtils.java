package com.henley.android.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 剪切板操作工具类
 *
 * @author Henley
 * @since 2020/5/26 14:22
 */
public final class ClipboardUtils {

    private ClipboardUtils() {
        throw new UnsupportedOperationException("Instantiation operation is not supported.");
    }

    /**
     * 将文字复制到剪切板
     */
    public static boolean copy(@NonNull Context context, CharSequence text) {
        return copy(context, ClipData.newPlainText(null, text));
    }

    /**
     * 将{@link Uri}复制到剪切板
     */
    public static boolean copy(@NonNull Context context, Uri uri) {
        return copy(context, ClipData.newUri(context.getContentResolver(), null, uri));
    }

    /**
     * 将{@link Intent}复制到剪切板
     */
    public static boolean copy(@NonNull Context context, Intent intent) {
        return copy(context, ClipData.newIntent(null, intent));
    }

    /**
     * 将{@link ClipData}复制到剪切板
     */
    public static boolean copy(@NonNull Context context, @Nullable ClipData clip) {
        if (clip != null) {
            ClipboardManager clipboard = getClipboardManager(context);
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取剪切板的文本
     */
    @Nullable
    public static CharSequence getText(@NonNull Context context) {
        ClipData clip = getClipData(context);
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).coerceToText(context);
        }
        return null;
    }

    /**
     * 获取剪切板的{@link Uri}
     */
    @Nullable
    public static Uri getUri(@NonNull Context context) {
        ClipData clip = getClipData(context);
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getUri();
        }
        return null;
    }

    /**
     * 获取剪切板的{@link Intent}
     */
    @Nullable
    public static Intent getIntent(@NonNull Context context) {
        ClipData clip = getClipData(context);
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getIntent();
        }
        return null;
    }

    /**
     * 获取剪切板的{@link ClipData}
     */
    @Nullable
    public static ClipData getClipData(@NonNull Context context) {
        ClipboardManager clipboard = getClipboardManager(context);
        if (clipboard != null && clipboard.hasPrimaryClip()) {
            return clipboard.getPrimaryClip();
        }
        return null;
    }

    /**
     * 返回{@link ClipboardManager}对象
     */
    private static ClipboardManager getClipboardManager(@NonNull Context context) {
        return (ClipboardManager) context.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
    }

}
