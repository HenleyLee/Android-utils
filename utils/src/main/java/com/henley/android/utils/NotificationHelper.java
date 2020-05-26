package com.henley.android.utils;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.core.app.NotificationManagerCompat;

/**
 * 通知管理工具类
 *
 * @author liyunlong
 * @since 2020/5/26 15:38
 */
public final class NotificationHelper {

    private static final String GET_SERVICE = "getService";
    private static final String INOTIFICATIONMANAGER_CLASS = "android.app.INotificationManager";
    private static final String ARE_NOTIFICATIONS_ENABLED_FOR_PACKAGE = "areNotificationsEnabledForPackage";

    /**
     * 用来判断是否开启通知权限
     */
    @SuppressLint({"ObsoleteSdkInt", "PrivateApi"})
    @SuppressWarnings("unchecked")
    public static boolean isNotificationEnabled(Context context) {
        context = context.getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // Android 4.4及以上版本
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { // Android 4.1及以上版本
            context = context.getApplicationContext();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String packageName = context.getPackageName();
            try {
                Class notificationManagerClass = Class.forName(NotificationManager.class.getName());
                Method getServiceMethod = notificationManagerClass.getMethod(GET_SERVICE);
                Object iNotificationManager = getServiceMethod.invoke(notificationManager);
                Class iNotificationManagerClass = Class.forName(INOTIFICATIONMANAGER_CLASS);
                int uid = context.getApplicationInfo().uid;
                Method areNotificationsEnabledForPackageMethod = iNotificationManagerClass.getMethod(ARE_NOTIFICATIONS_ENABLED_FOR_PACKAGE, String.class, Integer.TYPE);
                return (boolean) areNotificationsEnabledForPackageMethod.invoke(iNotificationManager, packageName, uid);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return true; // API内部会检查是否是系统级应用，非系统应用时uid不允许访问会抛异常
        } else { // Android 4.0及以下的版本不能判断通知是否开启
            return true;
        }
    }

    /**
     * 打开应用通知设置页面
     */
    public static void openNotificationSetting(Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
