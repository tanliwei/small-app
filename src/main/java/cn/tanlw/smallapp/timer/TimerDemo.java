package cn.tanlw.smallapp.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * Java定时器Timer,TimerTask每隔一段时间随机生成数字
 * https://www.cnblogs.com/stsinghua/p/6419357.html
 * @create 2018/6/5
 */
public class TimerDemo {
    //执行时间，时间单位为毫秒,读者可自行设定，不得小于等于0
    private static Integer cacheTime = 14400000;
    //延迟时间，时间单位为毫秒,读者可自行设定，不得小于等于0
    private static Integer delay = 1000;
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis()/1000);
//cacheTime重置，生成大于4个小时，小于5个小时的任意时间
                cacheTime = (int) (14400000 + Math.random() * 3600000);
                //你要执行的操作
            }
        }, 0,10*1000);
        System.out.println("======");
    }
}
