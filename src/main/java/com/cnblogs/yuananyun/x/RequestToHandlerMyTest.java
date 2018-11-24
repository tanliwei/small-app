package com.cnblogs.yuananyun.x;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockRequestDispatcher;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * https://stackoverflow.com/questions/11721622/how-do-i-pass-the-httpservletrequest-object-to-the-test-case
 * @create 2018/7/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages="com.cnblogs")
public class RequestToHandlerMyTest {

    @Test
    public void myTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("localhost");
        request.setRequestURI("/get/{id}");
        request.setQueryString("param1=value1&param");
        request.setMethod("GET");

//        MockRequestDispatcher dispatcher = new MockRequestDispatcher("");

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        try {
            dispatcherServlet.init();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        Method method = null;
        try {
            method = dispatcherServlet.getClass().getDeclaredMethod("getHandler", HttpServletRequest.class);
            ReflectionUtils.makeAccessible(method);
            HandlerExecutionChain executionChain = (HandlerExecutionChain) method.invoke(dispatcherServlet,request);
            System.out.println(executionChain);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
