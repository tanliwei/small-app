package com.cnblogs.yuananyun.x;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Locale;

/**
 * 如何在线程中获取spring 管理的bean -- https://www.cnblogs.com/zrui-xyu/p/6953012.html
 * Java（多）线程中注入Spring的Bean -- https://www.cnblogs.com/vinozly/p/5223147.html
 * Spring 获取 bean工具类
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-12 上午7:44
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    public static ApplicationContext getContext() {
        return context;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static <T> T getBean(String beanName){
        return (T) context.getBean(beanName);
    }

    //通过 clazzName 获取bean对象
    public static <T> T getBeanByClassName(String clazzName){
        try {
            Class clazz = Class.forName(clazzName);
            return (T) context.getBean(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMessage(String key){
        return context.getMessage(key, null, Locale.getDefault());
    }

}