package cn.tanlw.smallapp.browser;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

/**
 * java版selenium使用chromedriver抓取动态网页
 * https://blog.csdn.net/hechaojie_com/article/details/81741524
 * 
 * java.lang.IllegalAccessError: tried to access method com.google.common.util.concurrent.SimpleTimeLim
 * https://stackoverflow.com/questions/23957409/java-lang-illegalaccesserror-tried-to-access-method-com-google-common-collect-m
 */
public class ChromeMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        long time = System.currentTimeMillis();
        // 可省略，若驱动放在其他目录需指定驱动路径
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(chromeOptions);
        driver.get("http://baidu.com");
        // 休眠1s,为了让js执行完
        Thread.sleep(1000l);
        // 网页源码
        String source = driver.getPageSource();
        System.out.println(source);
        driver.close();
        System.out.println("耗时:"+(System.currentTimeMillis()-time));
    }
}
