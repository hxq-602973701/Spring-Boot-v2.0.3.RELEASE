package com.example.demo.web.utils;
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil extends ServletRequestUtils {
    public RequestUtil() {
    }

    public static boolean getBoolean(String paramName) {
        return getBoolean(paramName, false);
    }

    public static boolean getBoolean(String paramName, boolean defaultVal) {
        return getBooleanParameter(getRequest(), paramName, defaultVal);
    }

    public static long getLong(String paramName) {
        return getLong(paramName, 0L);
    }

    public static long getLong(String paramName, long defaultVal) {
        return getLongParameter(getRequest(), paramName, defaultVal);
    }

    public static int getInt(String paramName) {
        return getInt(paramName, 0);
    }

    public static int getInt(String paramName, int defaultVal) {
        return getIntParameter(getRequest(), paramName, defaultVal);
    }

    public static String getString(String paramName) {
        return getString(paramName, "");
    }

    public static String getString(String paramName, String defaultVal) {
        return getStringParameter(getRequest(), paramName, defaultVal);
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getRemoteIp() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        try {
            ip = ip.split(",")[0];
        } catch (Exception var3) {
            ip = request.getRemoteHost();
        }

        return ip;
    }

    public static String getUserAgent() {
        return getRequest().getHeader("User-Agent");
    }

//    public static DeviceTypeEnum getDeviceType() {
//        String userAgent = getRequest().getHeader("User-Agent");
//        return StringUtil.getDeviceType(userAgent);
//    }

    public static boolean isJsonRequest() {
        return getRequest().getPathInfo().toLowerCase().endsWith("json");
    }

    public static String getQueryString() {
        return getRequest().getQueryString();
    }
}
