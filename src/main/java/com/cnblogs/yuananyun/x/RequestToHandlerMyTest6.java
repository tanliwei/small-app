package com.cnblogs.yuananyun.x;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Mock对象 用反射调用方法 , 方法静态变量化 优化
 * Lazy Instantiation of the Spring MVC DispatcherServlet?
 * -- https://stackoverflow.com/questions/2113282/lazy-instantiation-of-the-spring-mvc-dispatcherservlet
 *
 * @create 2018/7/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.cnblogs")
public class RequestToHandlerMyTest6 {
    public RequestToHandlerMyTest6() {
    }


    private static Method methodInitStrategies;
    private static Method methodGetHandler;
    private static Method methodGetHandlerAdapter;
    private static Method methodHandle;


    private static final MockHttpServletResponse response = new MockHttpServletResponse();

    static {
        try {
            methodHandle = HandlerAdapter.class.getMethod("handle", HttpServletRequest.class, HttpServletResponse.class, Object.class);
            ReflectionUtils.makeAccessible(methodHandle);
            methodGetHandlerAdapter = DispatcherServlet.class.getDeclaredMethod("getHandlerAdapter", Object.class);
            ReflectionUtils.makeAccessible(methodGetHandlerAdapter);
            methodGetHandler = DispatcherServlet.class.getDeclaredMethod("getHandler", HttpServletRequest.class);
            ReflectionUtils.makeAccessible(methodGetHandler);
            methodInitStrategies = DispatcherServlet.class.getDeclaredMethod("initStrategies", ApplicationContext.class);
            ReflectionUtils.makeAccessible(methodInitStrategies);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void myTest() {

        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
//        request.setContent();
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setServerName("localhost");
            request.setRequestURI("/com/cnblogs/post");
            request.setContentType("application/json");
            request.setContent("{\"a\":\"abc\"}".getBytes());
//          request.setContent("{\"a\":\"c\"}".getBytes());
            request.setMethod("POST");
            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            try {
                methodInitStrategies.invoke(dispatcherServlet, SpringContextUtil.getContext());
                HandlerExecutionChain mappedHandler = (HandlerExecutionChain) methodGetHandler.invoke(dispatcherServlet, request);
//            System.out.println("mappedHandler:" + mappedHandler);
                if (mappedHandler == null) {

                }
                HandlerAdapter ha = (HandlerAdapter) methodGetHandlerAdapter.invoke(dispatcherServlet, mappedHandler.getHandler());
                methodHandle.invoke(ha, request, response, mappedHandler.getHandler());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                e.printStackTrace(new PrintStream(baos));
                String exception = baos.toString();
                exception = exception.length() > 2000 ? exception.substring(0, 2000) : exception;
                System.err.println(exception);
            }
        }
        Long endTime = System.currentTimeMillis();

        System.out.println("Used time:" + (endTime - startTime));
        //1189 1210 1231 1054 1112
    }

}
