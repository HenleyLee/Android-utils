package com.henley.android.utils;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * {@link Intent}操作工具类
 *
 * @author Henley
 * @since 2020/5/26 16:03
 */
public final class IntentUtils {

    /**
     * QQ包名
     */
    public static final String PACKAGENAME_QQ = "com.tencent.mobileqq";
    /**
     * 微信包名
     */
    public static final String PACKAGENAME_WEIXIN = "com.tencent.mm";
    /**
     * 支付宝包名
     */
    public static final String PACKAGENAME_ALIPAY = "com.eg.android.AlipayGphone";
    /**
     * 百度地图包名
     */
    public static final String PACKAGENAME_BaiduMap = "com.baidu.BaiduMap";
    /**
     * 高德地图包名
     */
    public static final String PACKAGENAME_AMaps = "com.autonavi.minimap";
    /**
     * Google Play包名
     */
    public static final String PACKAGENAME_GOOGLEPLAY = "com.android.vending";

    private IntentUtils() {
        throw new UnsupportedOperationException("Instantiation operation is not supported.");
    }

    /**
     * 在浏览器中搜索一个关键词
     */
    public static void search(Context context, String keyword) {
        try {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, keyword);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在浏览器中打开一个Url
     */
    public static void openWebKit(@NonNull Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (NullPointerException | ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开拨号界面
     */
    public static void openDial(@NonNull Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL_BUTTON);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开拨号界面
     */
    public static void openDial(@NonNull Context context, String phoneNumber) {
        try {
            Uri uri = Uri.parse("tel:" + phoneNumber);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开新信息界面
     */
    public static void openNewMessage(@NonNull Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setType("vnd.android-dir/mms-sms");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件
     */
    public static void sendEmail(@NonNull Context context, String[] recipient, String[] carbonCopy, String subject, String sendBody, String filePath) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, recipient); // 收件者
            intent.putExtra(Intent.EXTRA_CC, carbonCopy); // 抄送者
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, sendBody);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
            intent.setType("image/*");
            intent.setType("message/rfc882");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开通讯录
     */
    public static void openContacts(@NonNull Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException | SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开通话记录
     */
    public static void openCallRecord(@NonNull Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("vnd.android.cursor.dir/calls");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放多媒体
     */
    public static void playMultiMedia(@NonNull Context context, @NonNull String filePath) {
        try {
            Uri uri = Uri.fromFile(new File(filePath));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "audio/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开应用市场中的应用详情页
     */
    public static void openAppMarketDetail(@NonNull Context context) {
        openAppMarketDetail(context, context.getPackageName());
    }

    /**
     * 打开应用市场中的应用详情页
     */
    public static void openAppMarketDetail(@NonNull Context context, @NonNull String packageName) {
        try {
            String uriString = "market://details?id=" + packageName;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uriString));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开Google Play的应用详情页
     */
    public static void openGooglePlayDetail(@NonNull Context context) {
        openGooglePlayDetail(context, context.getPackageName());
    }

    /**
     * 打开Google Play的应用详情页
     */
    public static void openGooglePlayDetail(@NonNull Context context, @NonNull String packageName) {
        try {
            String uriString = "market://details?id=" + packageName;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uriString));
            intent.setPackage(PACKAGENAME_GOOGLEPLAY);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开应用市场中的搜索页搜索当前应用
     */
    public static void openAppMarketSearch(@NonNull Context context) {
        try {
            String uriString = "market://search?q=pname:" + context.getPackageName();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uriString));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开系统分享
     */
    public static void openShare(@NonNull Context context, String mainTitle, String title, String content) {
        openShare(context, mainTitle, title, content, new String[]{});
    }

    /**
     * 打开系统分享
     */
    public static void openShare(@NonNull Context context, String mainTitle, String title, String content, String imagePath) {
        openShare(context, mainTitle, title, content, new String[]{imagePath});
    }

    /**
     * 打开系统分享
     */
    public static void openShare(@NonNull Context context, String mainTitle, String title, String content, String[] imagePaths) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        if (imagePaths == null || imagePaths.length == 0) { // 纯文本分享
            intent.setType("text/plain");
        } else if (imagePaths.length > 0) { // 图文分享
            intent.setType("image/*");
            if (imagePaths.length == 1) {
                File file = new File(imagePaths[0]);
                if (file.exists() && file.isFile()) {
                    Uri uri = Uri.fromFile(new File(imagePaths[0]));
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                }
            } else {
                ArrayList<Uri> uriList = new ArrayList<>();
                for (String imagePath : imagePaths) {
                    File file = new File(imagePaths[0]);
                    if (file.exists() && file.isFile()) {
                        uriList.add(Uri.fromFile(new File(imagePath)));
                    }
                }
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
            }
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (TextUtils.isEmpty(mainTitle)) {
            mainTitle = context.getString(R.string.share_to);
        }
        context.startActivity(Intent.createChooser(intent, mainTitle));
    }

    /**
     * 回到Home，后台运行
     */
    public static void goHome(@NonNull Context context) {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(homeIntent);
    }

    /**
     * 打开系统设置界面
     */
    public static void openSettings(@NonNull Context context) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 根据action打开系统设置界面
     *
     * @param context 上下文
     * @param action  详细请查看{@link Settings}
     */
    public static void openSettings(@NonNull Context context, @NonNull String action) {
        Intent intent = new Intent(action);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 启动App设置授权界面
     */
    public static void startSettingIntent(@NonNull Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开QQ与指定用户的聊天界面
     */
    public static void QQChat(@NonNull Context context, String QQ) {
        if (!TextUtils.isEmpty(QQ)) {
            String url = String.format("mqqwpa://im/chat?chat_type=wpa&uin=%s", QQ);
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

    /**
     * 判断应用是否已安装
     */
    public static boolean checkApkInstalled(@NonNull Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            return context.getPackageManager().getApplicationInfo(packageName, 0) != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 判断是否有APP可以接受指定的Intent
     *
     * @param context 上下文
     * @param intent  Intent对象
     */
    public boolean isIntentAvailable(@NonNull Context context, @NonNull Intent intent) {
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list != null && list.size() > 0;
    }

}
