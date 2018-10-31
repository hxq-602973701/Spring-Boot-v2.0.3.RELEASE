//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.demo.web.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Strings;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonBinder {
    private static Logger logger = LoggerFactory.getLogger(JsonBinder.class);
    private static ObjectMapper mapper = createMapper();

    public JsonBinder() {
    }

    public static ObjectMapper createMapper() {
        if (mapper != null) {
            return mapper;
        } else {
            mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);
            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper;
        }
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        } else {
            try {
                return mapper.readValue(jsonString, clazz);
            } catch (IOException var3) {
                logger.warn("parse json string error:" + jsonString, var3);
                return null;
            }
        }
    }

    /** @deprecated */
    @Deprecated
    public static <T> T fromJson(String jsonString, TypeReference<?> valueTypeRef) {
        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        } else {
            try {
                return mapper.readValue(jsonString, valueTypeRef);
            } catch (IOException var3) {
                logger.warn("parse json string error:" + jsonString, var3);
                return null;
            }
        }
    }

    /** @deprecated */
    @Deprecated
    public static String toJson(Object object) {
        if (object == null) {
            return null;
        } else {
            try {
                return mapper.writeValueAsString(object);
            } catch (IOException var2) {
                logger.warn("write to json string error:" + object, var2);
                return null;
            }
        }
    }

    public static void setDateFormat(String pattern) {
        if (!Strings.isNullOrEmpty(pattern)) {
            DateFormat df = new SimpleDateFormat(pattern);
            mapper.setDateFormat(df);
        }

    }

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
