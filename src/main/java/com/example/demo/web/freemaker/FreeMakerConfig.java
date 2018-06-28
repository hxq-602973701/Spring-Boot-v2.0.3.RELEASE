package com.example.demo.web.freemaker;

import com.example.demo.dal.utils.FreemarkerStaticModels;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author lt
 * @date 2018/5/29
 */
@Configuration
//这种是注解方式
@ImportResource(locations= {"classpath:/templates/freemarker.xml"})
public class FreeMakerConfig {

    /**
     * 配置freeMarkerViewResolver
     *
     * @return
     * @throws IOException
     */
    @Bean
    public FreeMarkerViewResolver refFreemarkerStaticModels() throws IOException {

        InputStream in = PropertiesFactoryBean.class.getClassLoader().getResourceAsStream("config/freemarker_static.properties");
        Properties properties = new Properties();
        properties.load(in);
        FreemarkerStaticModels freemarkerStaticModels = FreemarkerStaticModels.getInstance();
        freemarkerStaticModels.setStaticModels(properties);

        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setCache(true);
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setContentType("text/html; charset=UTF-8");
        freeMarkerViewResolver.setExposeSpringMacroHelpers(true);
        freeMarkerViewResolver.setExposeRequestAttributes(true);
        freeMarkerViewResolver.setExposeSessionAttributes(true);
        freeMarkerViewResolver.setOrder(0);
        freeMarkerViewResolver.setAttributesMap(freemarkerStaticModels);
        return freeMarkerViewResolver;
    }

    /**
     * 配置freeMarkerConfigurer(这里主要配置的是freeMaker路径)
     *
     * @return
     */
    @Bean
    public FreeMarkerConfigurer FreeMarkerConfig() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:/templates/ftl");
        return freeMarkerConfigurer;
    }
}