package com.cnblogs.yuananyun;

import com.alibaba.fastjson.JSON;
import com.cnblogs.yuananyun.x.RequestToMethodItem;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;



/**
 * SpringMVC项目中获取所有URL到Controller Method的映射 -- https://www.cnblogs.com/yuananyun/archive/2014/08/25/3934371.html
 * @create 2018/7/5
 */
@RequestMapping(value="/com/cnblogs/")
@RestController
public class AllMyMapping {

    @GetMapping(value="/get")
    public String get(){
        System.out.println("GET");
        return "GET";
    }
    @GetMapping(value="/geT")
    public String geT(){
        System.out.println("GET");
        return "GET";
    }
    @GetMapping(value="/get/")
    public String getS(){
        System.out.println("GET");
        return "GET";
    }
    @GetMapping(value="/geTT")
    public String geTT(){
        System.out.println("GET");
        return "GET";
    }
    @GetMapping(value="/get////T")
    public String gettt(){
        System.out.println("GET");
        return "GET";
    }
    @GetMapping(value="/get/{id}")
    public String getPathParameter(@PathVariable Integer id){
        System.out.println("GET " + id);
        return "GET";
    }

    @PostMapping(value="/post")
    public String post(){
        System.out.println("POST");
        return "POST";
    }
    @RequestMapping(value="/request/")
    public String request(){
        System.out.println("request");
        return "POST";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request)
    {
        ServletContext servletContext = request.getSession().getServletContext();
        if (servletContext == null)
        {
            return null;
        }
        WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        //请求url和处理方法的映射
        List<RequestToMethodItem> requestToMethodItemList = new ArrayList<RequestToMethodItem>();
        //获取所有的RequestMapping
        Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(appContext,

                HandlerMapping.class, true, false);

        for (HandlerMapping handlerMapping : allRequestMappings.values())
        {
            //本项目只需要RequestMappingHandlerMapping中的URL映射
            if (handlerMapping instanceof RequestMappingHandlerMapping)
            {
                RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
                for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet())
                {
                    RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
                    HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();

                    RequestMethodsRequestCondition methodCondition = requestMappingInfo.getMethodsCondition();
                    String requestType = "";
                    if (methodCondition.getMethods().size() > 0) {
                        requestType = SetUtils.first(methodCondition.getMethods()).name();
                    }

                    PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
                    String requestUrl = SetUtils.first(patternsCondition.getPatterns());

                    String controllerName = mappingInfoValue.getBeanType().toString();
                    String requestMethodName = mappingInfoValue.getMethod().getName();
                    Class<?>[] methodParamTypes = mappingInfoValue.getMethod().getParameterTypes();
                    RequestToMethodItem item = new RequestToMethodItem(requestUrl, requestType, controllerName, requestMethodName,

                            methodParamTypes);

                    requestToMethodItemList.add(item);
                }
                break;
            }
        }
        return JSON.toJSONString(requestToMethodItemList);
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


    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    /**
     * SpringMVC项目中获取所有URL到Controller Method的映射 -- https://blog.csdn.net/zengfanwei1990/article/details/72643150
     * @param id
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @RequestMapping("/index2")
    public String index2(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {

        Map map =  this.handlerMapping.getHandlerMethods();
        Iterator<?> iterator = map.entrySet().iterator();
        Map mapResult = new HashMap();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            mapResult.put(entry.getKey().toString(), entry.getValue().toString());
            System.out.println(entry.getKey() +"\n" + entry.getValue());
        }
        return JSON.toJSONString(mapResult);
    }


    /**
     * Spring 反射得到所有controller与method -- http://183615215-qq-com.iteye.com/blog/1866281
     * @param request
     * @return
     */
    @RequestMapping("/index3")
    public Object getUrlMapping(HttpServletRequest request) {
        WebApplicationContext wc = getWebApplicationContext(request.getSession().getServletContext());
        RequestMappingHandlerMapping rmhp = wc.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = rmhp.getHandlerMethods();
        System.out.println("===============");
        for (Iterator<RequestMappingInfo> iterator = map.keySet().iterator(); iterator
                .hasNext();) {
            RequestMappingInfo info = iterator.next();
            System.out.print(info.getConsumesCondition());
            System.out.print(info.getCustomCondition());
            System.out.print(info.getHeadersCondition());
            System.out.print(info.getMethodsCondition());
            System.out.print(info.getParamsCondition());
            System.out.print(info.getPatternsCondition());
            System.out.print(info.getProducesCondition());

            System.out.print("===");

            HandlerMethod method = map.get(info);
            System.out.print(method.getBeanType().getName() + "--");
            System.out.print(method.getMethod().getName() + "--");
            if (method.getMethodAnnotation(RequestMapping.class).params()!=null &&
                    method.getMethodAnnotation(RequestMapping.class).params().length > 0) {
                System.out.print("a:"+method.getMethodAnnotation(RequestMapping.class).params()[0]);
            }
            System.out.println("b:"+JSON.toJSONString(method.getMethod().getParameterTypes()));
            System.out.println();
        }
        System.out.println("===============");
        return null;
    }

    public WebApplicationContext getWebApplicationContext(ServletContext sc) {
        return WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
    }
}
