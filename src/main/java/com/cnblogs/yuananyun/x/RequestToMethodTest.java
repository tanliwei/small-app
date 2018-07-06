package com.cnblogs.yuananyun.x;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @create 2018/7/5
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages="com.cnblogs")
public class RequestToMethodTest {

//    @Autowired
//    RequestMappingHandlerMapping requestMappingHandlerMapping;

    private static ExecutorService service = Executors.newCachedThreadPool();
    @Test
    public void invokeRestControllerMethod() {

        Map<RequestKey, RequestToMethodItem> urlMethodMapper = initialRequestMethodMapper();
        /**
         * 处理源地址 和 目标地址 最后多一个斜杠的问题
         */
        String input = "/com/cnblogs/get";
        RequestKey requestKey = new RequestKey(input,"GET");
        RequestToMethodItem target = getRequestToMethodItem(urlMethodMapper, requestKey);
        invoke(target);

        input = "/com/cnblogs/request/";
        requestKey = new RequestKey(input, "GET");
        target = getRequestToMethodItem(urlMethodMapper, requestKey);
        invoke(target);

    }

    private RequestToMethodItem getRequestToMethodItem(Map<RequestKey, RequestToMethodItem> urlMethodMapper, RequestKey requestKey) {
        RequestToMethodItem item = urlMethodMapper.get(requestKey);
        if (item == null) {
            requestKey.setRequestMethod("");//映射所有方法
            item = urlMethodMapper.get(requestKey);
        }
        return item;
    }


    private Object invoke(RequestToMethodItem target) {
        try {
            ScheduleRunnable runnable = new ScheduleRunnable(target.getControllerName(),target.getMethodName(), target.getMethodParmaTypes());
            Future future = service.submit(runnable);
            future.get();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<RequestKey, RequestToMethodItem> initialRequestMethodMapper() {
        Map<RequestKey, RequestToMethodItem> urlMethodMapper = new HashMap<>(1 << 6);
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) SpringContextUtil.getBeanByClassName(RequestMappingHandlerMapping.class.getCanonicalName());
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        RequestKey requestKey;
        for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()) {
            RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
            HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();

            RequestMethodsRequestCondition methodCondition = requestMappingInfo.getMethodsCondition();
            String requestType = "";
            if (methodCondition.getMethods().size() > 0) {
                requestType = SetUtils.first(methodCondition.getMethods()).name();
            }

            PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
            String requestUrl = SetUtils.first(patternsCondition.getPatterns());
            requestUrl = deleteLashSlash(requestUrl);
            String controllerName = mappingInfoValue.getBeanType().getName();
            String requestMethodName = mappingInfoValue.getMethod().getName();//TODO NOT POST GET PUT
            Class<?>[] methodParamTypes = mappingInfoValue.getMethod().getParameterTypes();
            RequestToMethodItem item = new RequestToMethodItem(requestUrl, requestType, controllerName, requestMethodName,
                    methodParamTypes);
            requestKey = new RequestKey(requestUrl, requestType);
            urlMethodMapper.put(requestKey,item);
        }
        return urlMethodMapper;
    }

    private String deleteLashSlash(String requestUrl) {
        if (StringUtils.isEmpty(requestUrl)) {
            return requestUrl;
        }
        return requestUrl.replace("","");
    }

    private static class SetUtils {
        public static <T> T first(Set<T> methods) {
            Iterator itr = methods.iterator();
            if (itr.hasNext()) {
                return (T) itr.next();
            }
            return (T) "";
        }
    }

    private class RequestKey {
        private String requestUrl;
        private String requestMethod;

        public void setRequestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
        }

        public RequestKey(String requestUrl, String requestMethod){
            this.requestUrl = requestUrl;
            this.requestMethod = requestMethod;
        }

        @Override
        public int hashCode() {
            return requestUrl.hashCode() * 3 + requestMethod.hashCode() * 7;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (! (obj instanceof RequestKey)) {
                return false;
            }
            if (!this.requestUrl.equals(((RequestKey) obj).requestUrl)) {
                return false;
            }
            if (!this.requestMethod.equals(((RequestKey) obj).requestMethod)) {
                return false;
            }
            return true;
        }
    }

    @Test
    public void requestKeyTest(){
        RequestToMethodTest.RequestKey key = new RequestToMethodTest.RequestKey("a","b");
        RequestToMethodTest.RequestKey key2 = new RequestToMethodTest.RequestKey("a","b");
        System.out.println(key == key2);
        Map<RequestToMethodTest.RequestKey,String> data = new HashMap<>();
        data.put(key,"a");
        System.out.println("result:"+data.get(key2));
    }
}
