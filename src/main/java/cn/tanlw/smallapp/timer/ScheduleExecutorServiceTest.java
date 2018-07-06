package cn.tanlw.smallapp.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author tanliwei
 * @create 2018/6/7
 */
public class ScheduleExecutorServiceTest {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis()/1000);
            }
        }, 0, 10, TimeUnit.SECONDS);
    }
}
