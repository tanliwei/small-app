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
 * Mock对象 用反射调用方法
 * Lazy Instantiation of the Spring MVC DispatcherServlet?
 * -- https://stackoverflow.com/questions/2113282/lazy-instantiation-of-the-spring-mvc-dispatcherservlet
 *
 * @create 2018/7/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.cnblogs")
public class RequestToHandlerMyTest4 {

    @Test
    public void myTest() {
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {


            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setServerName("localhost");
            request.setRequestURI("/com/cnblogs/post");
            request.setContentType("application/json");
            request.setContent("{\"a\":\"abc\"}".getBytes());
//          request.setContent("{\"a\":\"c\"}".getBytes());
            request.setMethod("POST");

            MockHttpServletResponse response = new MockHttpServletResponse();
//        request.setContent();

            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            try {
                Method methodInitStrategies = dispatcherServlet.getClass().getDeclaredMethod("initStrategies", ApplicationContext.class);
                ReflectionUtils.makeAccessible(methodInitStrategies);
                methodInitStrategies.invoke(dispatcherServlet, SpringContextUtil.getContext());

                Method methodGetHandler = dispatcherServlet.getClass().getDeclaredMethod("getHandler", HttpServletRequest.class);

                ReflectionUtils.makeAccessible(methodGetHandler);
                HandlerExecutionChain mappedHandler = (HandlerExecutionChain) methodGetHandler.invoke(dispatcherServlet, request);
//                System.out.println("mappedHandler:" + mappedHandler);
                if (mappedHandler == null) {

                }

                Method methodGetHandlerAdapter = dispatcherServlet.getClass().getDeclaredMethod("getHandlerAdapter", Object.class);

                ReflectionUtils.makeAccessible(methodGetHandlerAdapter);
                HandlerAdapter ha = (HandlerAdapter) methodGetHandlerAdapter.invoke(dispatcherServlet, mappedHandler.getHandler());

                Method methodHandle = ha.getClass().getMethod("handle", HttpServletRequest.class, HttpServletResponse.class, Object.class);
                ReflectionUtils.makeAccessible(methodHandle);
                methodHandle.invoke(ha, request, response, mappedHandler.getHandler());

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
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
        //1686 1667 1529 1585 1618
        //1671 1519 1688 1612 1508

    }

}
