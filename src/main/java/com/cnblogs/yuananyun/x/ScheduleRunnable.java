package com.cnblogs.yuananyun.x;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 执行定时任务
 * https://www.cnblogs.com/jurendage/p/9153835.html -- Java开源生鲜电商平台-定时器,定时任务quartz的设计与架构(源码可下载）
 * @author Administrator
 *
 */
public class ScheduleRunnable implements Callable {
    private Object target;
    private Method method;
    private Class<?>[]  params;

    public ScheduleRunnable(String beanName, String methodName, Class<?>[]  params)
            throws NoSuchMethodException, SecurityException {
        this.target = SpringContextUtil.getBeanByClassName(beanName);
        this.params = params;

        if (!StringUtils.isEmpty(params)) {
            this.method = target.getClass().getDeclaredMethod(methodName, params);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    /**
     * java捕获线程异常 -- https://blog.csdn.net/liuxiao723846/article/details/51902636
     * @return
     * @throws Exception
     */
    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        //模拟子线程执行任务耗时
//        Thread.sleep(1000*10);
        ReflectionUtils.makeAccessible(method);
        if (!StringUtils.isEmpty(params)) {
            method.invoke(target, params);
        } else {
            method.invoke(target);
        }
        return "OK";
    }
}
