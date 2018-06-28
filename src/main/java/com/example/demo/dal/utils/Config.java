package com.example.demo.dal.utils;

/**
 * Created by Administrator on 2018/1/30.
 */

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.ResourceUtils;

public final class Config {
    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private static String RUN_INSTANCE_NAME = "tomcat";
    private static Properties props = new Properties();

    private Config() {
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key);

        try {
            return converToBoolean(value);
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        String value = getString(key);

        try {
            return converToInteger(value);
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public static long getLong(String key) {
        return getLong(key, 0L);
    }

    public static long getLong(String key, long defaultValue) {
        String value = getString(key);

        try {
            return converToLong(value);
        } catch (Exception var5) {
            return defaultValue;
        }
    }

    public static String getString(String key) {
        return getString(key, (String) null);
    }

    public static String getString(String key, String defaultValue) {
        String value = props.getProperty(key);
        if (value == null) {
            value = defaultValue;
        }

        return value;
    }

    public static boolean isDebugMode() {
        String value = props.getProperty("config.run_mode");
        if ("debug".equalsIgnoreCase(value)) {
            return true;
        } else if ("online".equalsIgnoreCase(value)) {
            return false;
        } else {
            logger.error("config.run_mode config file error.");
            return false;
        }
    }

    public static String getRunInstance() {
        MDC.put("RUN_INSTANCE_NAME", RUN_INSTANCE_NAME);
        return RUN_INSTANCE_NAME;
    }

    private static boolean converToBoolean(String value) {
        String tmp = value.toLowerCase();
        if (tmp.equals("true")) {
            return true;
        } else if (tmp.equals("false")) {
            return false;
        } else {
            throw new RuntimeException("class not matching.");
        }
    }

    private static int converToInteger(String str) throws Exception {
        return Integer.parseInt(str);
    }

    private static long converToLong(String str) throws Exception {
        return Long.parseLong(str);
    }

    static {
        try {
            File e = ResourceUtils.getFile("classpath:config/config.properties");
            props.load(new FileInputStream(e));
            Matcher matcher = Pattern.compile("tomcat-node[0-9]").matcher(e.getCanonicalPath());
            if (matcher.find()) {
                RUN_INSTANCE_NAME = matcher.group();
            }

            MDC.put("RUN_INSTANCE_NAME", RUN_INSTANCE_NAME);
        } catch (Exception var2) {
            logger.error("init config file error", var2);
        }

    }
}