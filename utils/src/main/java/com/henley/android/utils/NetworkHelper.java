package com.henley.android.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

import androidx.annotation.RequiresPermission;

/**
 * 判断网络状态的工具类
 *
 * @author liyunlong
 * @since 2020/5/25 14:24
 */
public final class NetworkHelper {

    public static final String DNS1 = "dns1";
    public static final String DNS2 = "dns2";
    public static final String MAC_ETH1 = "eth1";
    public static final String MAC_WLAN0 = "wlan0";
    private static final String MAC_DEFAULT = "02:00:00:00:00:00";

    private NetworkHelper() {
        throw new UnsupportedOperationException("Instantiation operation is not supported.");
    }

    public enum NetworkType {

        NONE("NONE"),
        UNKNOWN("UNKNOWN"),
        WIFI("WIFI"),
        MOBILE_2G("2G"),
        MOBILE_3G("3G"),
        MOBILE_4G("4G");

        private String name;

        NetworkType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 获取ConnectivityManager对象
     */
    public static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 判断网络是否是4G
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean is4G(Context context) {
        ConnectivityManager connectivityManager = getConnectivityManager(context);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
    }

    /**
     * 返回SIM卡提供程序的MCC+MNC(移动国家代码+移动网络代码)
     */
    public static String getSimOperator(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager == null ? "" : telephonyManager.getSimOperator();
    }

    /**
     * 返回服务提供者名称(SPN)
     */
    public static String getSimOperatorName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager == null ? "" : telephonyManager.getSimOperatorName();
    }

    /**
     * 判断网络是否可以使用
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager manager = getConnectivityManager(context);
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();// 后去可用的网络信息
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) { // 判断网络是否可用
                return true;
            }
        }
        return false;
    }

    /**
     * 判断网络是否已连接
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = getConnectivityManager(context);
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED;
            }
        }
        return false;
    }

    /**
     * 判断WiFi是否可用
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isWiFiAvailable(Context context) {
        if (context != null) {
            ConnectivityManager manager = getConnectivityManager(context);
            NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); // 得到与WiFi相关的网络信息
            if (networkInfo != null) {
                return networkInfo.isAvailable();// 判断网络是否可用
            }
        }
        return false;
    }

    /**
     * 判断WiFi是否已连接
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isWiFiConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = getConnectivityManager(context);
            NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);// 得到与WiFi相关的网络信息
            if (networkInfo != null) {
                return networkInfo.isConnected();// 判断网络是否已连接
            }
        }
        return false;
    }

    /**
     * 获得WiFi的连接状态
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static NetworkInfo.State getWiFiNetState(Context context) {
        if (context != null) {
            ConnectivityManager manager = getConnectivityManager(context);
            NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo != null) {
                return networkInfo.getState();// 得到当前网络的连接状态
            }
        }
        return null;
    }

    /**
     * 判断Mobile是否可用
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isMobileAvailable(Context context) {
        if (context != null) {
            ConnectivityManager manager = getConnectivityManager(context);
            NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断Mobile是否已连接
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = getConnectivityManager(context);
            NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (networkInfo != null) {
                return networkInfo.isConnected();
            }
        }
        return false;
    }

    /**
     * 获得Mobile的连接状态
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static NetworkInfo.State getMobileNetState(Context context) {
        if (context != null) {
            ConnectivityManager manager = getConnectivityManager(context);
            NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (networkInfo != null) {
                return networkInfo.getState();
            }
        }
        return null;
    }

    /**
     * 判断当前的网络类型(WiFi还是Mobile)
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static String getNetTypeName(Context context) {
        if (context != null) {
            ConnectivityManager manager = getConnectivityManager(context);
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return activeNetworkInfo.getTypeName();
            }
        }
        return null;
    }

    /**
     * 打开网络设置界面
     */
    public static void openNetSetting(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 获取设备Mac地址
     *
     * @see #getMacAddress(String)
     */
    public static String getMacAddress() {
        return getMacAddress(MAC_WLAN0);
    }

    /**
     * 获取设备Mac地址
     */
    public static String getMacAddress(String interfaceName) {
        String macAddress = MAC_DEFAULT;
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : interfaces) {
                if (networkInterface == null || networkInterface.getName() == null) {
                    continue;
                }
                if (!networkInterface.getName().equalsIgnoreCase(interfaceName)) {
                    continue;
                }
                byte[] macBytes = networkInterface.getHardwareAddress();
                if (macBytes == null) {
                    continue;
                }
                StringBuilder builder = new StringBuilder();
                for (byte b : macBytes) {
                    builder.append(String.format("%02X:", b));
                }
                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                macAddress = builder.toString();
                break;
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return macAddress;
    }

    /**
     * 获取设备IP地址
     *
     * @see #getIpAddress(boolean)
     */
    public static String getIpAddress() {
        return getIpAddress(true);
    }

    /**
     * 获取设备IP地址
     */
    public static String getIpAddress(boolean isIPv4Address) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : interfaces) {
                List<InetAddress> addresses = Collections.list(networkInterface.getInetAddresses());
                for (InetAddress inetAddress : addresses) {
                    if (inetAddress.isLoopbackAddress() || inetAddress.isLinkLocalAddress()) {
                        continue;
                    }
                    if (isIPv4Address && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    } else if (inetAddress instanceof Inet6Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取本地DNS
     */
    public static String getLocalDns(String dns) {
        StringBuilder builder = new StringBuilder();
        Process process = null;
        BufferedReader reader = null;
        try {
            process = Runtime.getRuntime().exec("getprop net." + dns);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (Exception ignore) {
                // ignore
            }
        }
        return builder.toString().trim();
    }

    /**
     * 返回Ping结果
     */
    public static String getPingResult(String ping) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(ping);
            int status = process.waitFor();
            if (status == 0) {
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            }
            String line;
            while ((line = reader.readLine()) != null) {
                // 主宿机的IP冲突，或者网关有重复的路由设置，也或者路由器堵塞比较厉害，也可能还有其他原因会导致ping包的时候收到多个重复值回应
                if (!TextUtils.isEmpty(line) && !line.contains("(DUP!)")) { // 过滤重复回应
                    builder.append(line);
                    if (!TextUtils.isEmpty(line)) {
                        builder.append("\r\n");
                    }
                }
            }
            reader.close();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (Exception ignore) {
                // ignore
            }
        }
        return builder.toString().trim();
    }

    /**
     * 返回Ping结果
     */
    public static int getPingStatus(String ping) {
        Process process = null;
        int status = -1;
        try {
            process = Runtime.getRuntime().exec(ping);
            status = process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (process != null) {
                    process.destroy();
                }
            } catch (Exception ignore) {
                // ignore
            }
        }
        return status;
    }

    /**
     * 返回Ping命令
     *
     * @param ip IP地址
     * @see #getPingCommand(int, int, int, String)
     */
    public static String getPingCommand(String ip) {
        return getPingCommand(4, 64, 1, ip);
    }

    /**
     * 返回Ping命令
     *
     * @param count 发送数据包的数量
     * @param size  发送数据包的大小(单位：byte)
     * @param time  发送数据包的时间间隔(单位：秒)
     * @param ip    IP地址
     */
    public static String getPingCommand(int count, int size, int time, String ip) {
        return "ping" + " -c " + count + " -s " + size + " -i " + time + " " + ip;
    }

}
