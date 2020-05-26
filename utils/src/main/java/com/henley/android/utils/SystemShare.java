package com.henley.android.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;

/**
 * 系统分享辅助类
 *
 * @author liyunlong
 * @since 2020/5/26 15:56
 */
public final class SystemShare {

    private static final String TAG = "SystemShare";

    /**
     * 当前Activity对象
     */
    private Activity activity;
    /**
     * 分享内容类型
     */
    @ShareType
    private String contentType;
    /**
     * 分享标题
     */
    private String title;
    /**
     * 分享内容
     */
    private String contentText;
    /**
     * 分享文件Uri
     */
    private Uri shareFileUri;
    /**
     * 分享到指定组件
     */
    private ComponentName componentName;
    /**
     * 分享完成后{@link Activity#onActivityResult(int, int, Intent)}的{@code requestCode}
     */
    private int requestCode;
    /**
     * 是否强制使用系统选择器
     */
    private boolean forcedUseSystemChooser;

    public static Builder newBuilder(Activity activity) {
        return new Builder(activity);
    }

    private SystemShare(@NonNull Builder builder) {
        this.activity = builder.activity;
        this.contentType = builder.shareContentType;
        this.title = builder.title;
        this.shareFileUri = builder.shareFileUri;
        this.contentText = builder.textContent;
        this.componentName = builder.componentName;
        this.requestCode = builder.requestCode;
        this.forcedUseSystemChooser = builder.forcedUseSystemChooser;
    }

    /**
     * 分享
     */
    public void share() {
        if (checkShareParam()) {
            Intent shareIntent = createShareIntent();

            if (shareIntent == null) {
                Log.e(TAG, "shareBySystem cancel.");
                return;
            }

            if (title == null) {
                title = "";
            }

            if (forcedUseSystemChooser) {
                shareIntent = Intent.createChooser(shareIntent, title);
            }

            if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
                try {
                    if (requestCode != -1) {
                        activity.startActivityForResult(shareIntent, requestCode);
                    } else {
                        activity.startActivity(shareIntent);
                    }
                } catch (Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        }
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.addCategory(Intent.CATEGORY_DEFAULT);
        shareIntent.setType(contentType);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (componentName != null) {
            shareIntent.setComponent(componentName);
        }
        switch (contentType) {
            case ShareType.TEXT:
                shareIntent.putExtra(Intent.EXTRA_TEXT, contentText);
                break;
            case ShareType.IMAGE:
            case ShareType.AUDIO:
            case ShareType.VIDEO:
            case ShareType.FILE:
                shareIntent.putExtra(Intent.EXTRA_STREAM, shareFileUri);
                Log.d(TAG, "Share uri: " + shareFileUri.toString());
                if (SDKHelper.isAtLeastN()) {
                    List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        activity.grantUriPermission(packageName, shareFileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }
                break;
            default:
                Log.e(TAG, contentType + " is not support share type.");
                shareIntent = null;
                break;
        }

        return shareIntent;
    }


    private boolean checkShareParam() {
        if (this.activity == null) {
            Log.e(TAG, "activity must not be null.");
            return false;
        }

        if (TextUtils.isEmpty(this.contentType)) {
            Log.e(TAG, "Share content type must not be null or empty.");
            return false;
        }

        if (ShareType.TEXT.equals(contentType)) {
            if (TextUtils.isEmpty(contentText)) {
                Log.e(TAG, "Share text context must not be null or empty.");
                return false;
            }
        } else {
            if (this.shareFileUri == null) {
                Log.e(TAG, "Share file path must not be null.");
                return false;
            }
        }

        return true;
    }

    public static class Builder {

        private Activity activity;
        @ShareType
        private String shareContentType = ShareType.FILE;
        private String title;
        private Uri shareFileUri;
        private String textContent;
        private int requestCode = -1;
        private ComponentName componentName;
        private boolean forcedUseSystemChooser = true;

        Builder(Activity activity) {
            this.activity = activity;
        }

        /**
         * 设置分享内容类型
         *
         * @param shareContentType 分享内容类型
         * @see ShareType
         */
        public Builder setShareContentType(@ShareType String shareContentType) {
            this.shareContentType = shareContentType;
            return this;
        }

        /**
         * 设置分享标题
         *
         * @param title 分享标题
         */
        public Builder setTitle(@NonNull String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置分享文件Uri
         *
         * @param shareFileUri 分享文件Uri
         */
        public Builder setShareFileUri(Uri shareFileUri) {
            this.shareFileUri = shareFileUri;
            return this;
        }

        /**
         * 设置分享内容
         *
         * @param textContent 分享内容
         */
        public Builder setTextContent(String textContent) {
            this.textContent = textContent;
            return this;
        }

        /**
         * 设置分享到的指定组件
         *
         * @param packageName 组件包名
         * @param className   组件类名
         */
        public Builder setShareToComponent(String packageName, String className) {
            this.componentName = new ComponentName(packageName, className);
            return this;
        }

        /**
         * 设置分享到的指定组件
         *
         * @param componentName 组件
         */
        public Builder setShareToComponent(ComponentName componentName) {
            this.componentName = componentName;
            return this;
        }

        /**
         * 设置分享完成后{@link Activity#onActivityResult(int, int, Intent)}的{@code requestCode}
         *
         * @param requestCode 请求码(默认为-1)
         */
        public Builder setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        /**
         * 设置是否强制使用系统选择器
         *
         * @param enable 是否强制使用系统选择器(默认为true)
         */
        public Builder forcedUseSystemChooser(boolean enable) {
            this.forcedUseSystemChooser = enable;
            return this;
        }

        /**
         * 构建SystemShare
         */
        public SystemShare build() {
            return new SystemShare(this);
        }

    }

    /**
     * 分享内容类型
     */
    @StringDef({
            ShareType.TEXT,
            ShareType.IMAGE,
            ShareType.AUDIO,
            ShareType.VIDEO,
            ShareType.FILE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShareType {

        /**
         * Text
         */
        String TEXT = "text/plain";

        /**
         * Image
         */
        String IMAGE = "image/*";

        /**
         * Audio
         */
        String AUDIO = "audio/*";

        /**
         * Video
         */
        String VIDEO = "video/*";

        /**
         * File
         */
        String FILE = "*/*";

    }

}
