package me.zhengjie.utils;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 * @Description
 * @Author fly
 * @Date 2021/4/13 16:06
 */
public class SpringContextUtil {
    private static ApplicationContext applicationContext;


    /**
     * 获取上下文
     * @return /
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 设置上下文
     * @param applicationContext /
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 通过名字获取上下文中的bean
     * @param name /
     * @return /
     */
    public static Object getBean(String name) {
        try {
            return applicationContext.getBean(name);
        } catch (NoSuchBeanDefinitionException ex) {
            return null;
        }
    }



    /**
     * 通过类型获取上下文中的bean
     * @param requiredType /
     * @return /
     */
    public static Object getBean(Class<?> requiredType) {
        return applicationContext.getBean(requiredType);
    }
}
