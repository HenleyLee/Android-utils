package com.henley.android.utils;

import android.net.Uri;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Url处理辅助类
 *
 * @author liyunlong
 * @since 2020/5/26 15:57
 */
public final class UrlHelper {

    /**
     * HTTP协议
     */
    public static final String PROTOCOL_HTTP = "http://";
    /**
     * HTTPS协议
     */
    public static final String PROTOCOL_HTTPS = "https://";
    /**
     * FILE协议
     */
    public static final String PROTOCOL_FILE = "file://";

    /**
     * 判断Url是否为空
     */
    public static boolean isUrlEmpty(String url) {
        return TextUtils.isEmpty(url)
                || TextUtils.isEmpty(url.trim())
                || PROTOCOL_HTTP.equals(url)
                || PROTOCOL_HTTPS.equals(url);
    }

    /**
     * 获取baseUrl
     */
    public static String getBaseUrl(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            return baseUrl;
        }
        baseUrl = getFullUrlWithProtocol(baseUrl);
        if (!baseUrl.endsWith("/")) {
            baseUrl = baseUrl + "/";
        }
        return baseUrl;
    }

    /**
     * 返回完整的Url(检查Url是否包含协议)
     */
    public static String getFullUrlWithProtocol(String url) {
        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(url.trim())) {
            url = url.trim();
            if (!url.startsWith(PROTOCOL_HTTP) && !url.startsWith(PROTOCOL_HTTPS) && !url.startsWith(PROTOCOL_FILE)) {
                if (url.contains("//")) {
                    url = url.replaceAll("//", "/");
                }
                url = PROTOCOL_HTTPS + url;
            }
        }
        return url;
    }


    /**
     * 返回完整Url(检查Url是否包含域名，并强制替换为当前域名)
     */
    public static String getFullUrlWithDomain(String url, String domain, boolean replace) {
        if (!TextUtils.isEmpty(url) && !url.startsWith(PROTOCOL_FILE)) {
            String host = Uri.parse(url).getHost();
            if (host == null || TextUtils.isEmpty(host)) { // 判断域名是否为空
                if (url.startsWith("//")) { // 判断Url是否以//开头
                    url = url.replace("//", "/");
                }
                url = domain + url; // 拼接域名
            } else if (replace) {
                String useHost = Uri.parse(domain).getHost();
                if (useHost != null && !TextUtils.equals(host, useHost)) { // 判断域名是否相同
                    url = url.replace(host, useHost);
                }
            }
        }
        url = getFullUrlWithProtocol(url);
        return url;
    }

    public static String encode(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        try {
            return URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            @SuppressWarnings("deprecated")
            String encode = URLEncoder.encode(content);
            return encode;
        }
    }

    public static String decode(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        try {
            return URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            @SuppressWarnings("deprecated")
            String decode = URLDecoder.decode(content);
            return decode;
        }
    }

}
