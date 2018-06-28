package com.example.demo.dal.utils;

import com.example.demo.dal.utils.Config;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateHashModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * 数据模板初始化
 */
public class FreemarkerStaticModels extends HashMap<String, Object> {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = -980045483290784020L;

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerStaticModels.class);

    private final static FreemarkerStaticModels FREEMARKER_STATIC_MODELS = new FreemarkerStaticModels();

    private Properties staticModels;

    /**
     * 私有化构造函数
     */
    private FreemarkerStaticModels() {
        // 系统名称
        put("SYSTEM_NAME", Config.getString("config.system.name"));
        // 资源时间戳
        put("RES_TIMESTAMP", Config.getString("config.resource.timestamp", Long.toString(System.currentTimeMillis())));
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static FreemarkerStaticModels getInstance() {
        return FREEMARKER_STATIC_MODELS;
    }

    private static TemplateHashModel useStaticPackage(String packageName) {
        try {
            BeansWrapperBuilder builder = new BeansWrapperBuilder(Configuration.VERSION_2_3_23);
            BeansWrapper wrapper = builder.build();
            TemplateHashModel staticModels = wrapper.getStaticModels();
            TemplateHashModel fileStatics = (TemplateHashModel) staticModels.get(packageName);
            return fileStatics;
        } catch (Exception e) {
            logger.error("packageName:" + packageName, e);
        }
        return null;
    }

    /**
     * 设置工具方法
     *
     * @param staticModels
     */
    public void setStaticModels(Properties staticModels) {
        if (this.staticModels == null && staticModels != null) {
            this.staticModels = staticModels;
            Set<String> keys = this.staticModels.stringPropertyNames();
            for (String key : keys) {
                FREEMARKER_STATIC_MODELS.put(key, useStaticPackage(this.staticModels.getProperty(key)));
            }
        }
    }
}
