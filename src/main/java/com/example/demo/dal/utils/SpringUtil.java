package com.example.demo.dal.utils;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

@Component
final class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext appContext = null;

    public SpringUtil() {
        System.out.println("SpringUtil加载.....");
    }

    public static Object getBean(String beanName) {
        return appContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {
        return appContext.getBean(clazz);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return (T) appContext.getBean(beanName);
    }

    public static void registerBean(String id, Class<?> beanClass) {
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ((ConfigurableApplicationContext) appContext).getBeanFactory();
        registry.registerBeanDefinition(id, new AnnotatedGenericBeanDefinition(beanClass));
    }

    public static boolean containsBean(String id) {
        return appContext.containsBean(id);
    }

    public static ApplicationContext getAppContext() {
        return appContext;
    }

    public static ApplicationContext getAppContext(ServletContext servletContext) {
        return WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }
}
