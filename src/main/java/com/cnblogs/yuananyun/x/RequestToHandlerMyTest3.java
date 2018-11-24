package com.cnblogs.yuananyun.x;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用反射
 * Lazy Instantiation of the Spring MVC DispatcherServlet?
 * -- https://stackoverflow.com/questions/2113282/lazy-instantiation-of-the-spring-mvc-dispatcherservlet
 * @create 2018/7/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages="com.cnblogs")
public class RequestToHandlerMyTest3 {

    @Test
    public void myTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("localhost");
        request.setRequestURI("/com/cnblogs/get/aa");
        request.setQueryString("param1=value1&param");
        request.setMethod("GET");
//        request.setContent();

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        try {
            Method method = dispatcherServlet.getClass().getDeclaredMethod("initStrategies", ApplicationContext.class);
            ReflectionUtils.makeAccessible(method);
            method.invoke(dispatcherServlet, SpringContextUtil.getContext());

            Method method2 = dispatcherServlet.getClass().getDeclaredMethod("getHandler", HttpServletRequest.class);
            ReflectionUtils.makeAccessible(method2);
            HandlerExecutionChain chain = (HandlerExecutionChain)method2.invoke(dispatcherServlet, request);
            System.out.println("chain:"+chain);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

}
