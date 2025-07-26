package com.meta.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Spring 应用上下文工具类
 * 提供从Spring容器中获取Bean的静态方法
 */
@Component
public class AppContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        AppContext.applicationContext = applicationContext;
    }

    /**
     * 获取ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 根据名称获取Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 根据类型获取Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据名称和类型获取Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        checkApplicationContext();
        return applicationContext.getBean(name, clazz);
    }

    /**
     * 检查ApplicationContext是否注入
     */
    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext未注入，请在Spring配置中定义AppContext");
        }
    }

    /**
     * 获取当前环境profile
     */
    public static String getActiveProfile() {
        checkApplicationContext();
        return applicationContext.getEnvironment().getActiveProfiles()[0];
    }

    /**
     * 获取配置属性
     */
    public static String getProperty(String key) {
        checkApplicationContext();
        return applicationContext.getEnvironment().getProperty(key);
    }

    // 获取所有实现某接口的Bean
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        checkApplicationContext();
        return applicationContext.getBeansOfType(clazz);
    }

    // 获取所有注解了特定注解的Bean
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        checkApplicationContext();
        return applicationContext.getBeansWithAnnotation(annotationType);
    }

    // 发布事件
    public static void publishEvent(Object event) {
        checkApplicationContext();
        applicationContext.publishEvent(event);
    }
}
