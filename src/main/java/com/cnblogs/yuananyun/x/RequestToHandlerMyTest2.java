package com.cnblogs.yuananyun.x;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 复制源码
 * @create 2018/7/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages="com.cnblogs")
public class RequestToHandlerMyTest2 {

    @Test
    public void myTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("localhost");
        request.setRequestURI("/com/cnblogs/get/aa");
        request.setQueryString("param1=value1&param");
        request.setMethod("GET");

        MockHttpServletResponse response = new MockHttpServletResponse();

//        MockRequestDispatcher dispatcher = new MockRequestDispatcher("");

        List<HandlerMapping> handlerMappings = new ArrayList();

        Map<String, HandlerMapping> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(SpringContextUtil.getContext(), HandlerMapping.class, true, false);
        if (!matchingBeans.isEmpty()) {
            handlerMappings = new ArrayList<HandlerMapping>(matchingBeans.values());
            // We keep HandlerMappings in sorted order.
            AnnotationAwareOrderComparator.sort(handlerMappings);
        }
        HandlerExecutionChain handler = null;
        for (HandlerMapping hm : handlerMappings) {
            try {
                handler = hm.getHandler(request);
                if (handler != null) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(handler);
        // Find all HandlerAdapters in the ApplicationContext, including ancestor contexts.
        Map<String, HandlerAdapter> matchingBeans2 =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(SpringContextUtil.getContext(), HandlerAdapter.class, true, false);
        List<HandlerAdapter> handlerAdapters = new ArrayList<>();
        if (!matchingBeans2.isEmpty()) {
            handlerAdapters = new ArrayList<HandlerAdapter>(matchingBeans2.values());
            // We keep HandlerAdapters in sorted order.
            AnnotationAwareOrderComparator.sort(handlerAdapters);
        }
        for (HandlerAdapter ha : handlerAdapters) {
            if (ha.supports(handler)) {
                System.out.println("ha:"+ha);
                break;
            }
        }


    }

}
