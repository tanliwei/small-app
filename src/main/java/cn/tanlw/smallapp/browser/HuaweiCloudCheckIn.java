package cn.tanlw.smallapp.browser;

import cn.tanlw.util.DESStringUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * 华为云签到服务
 * Todo Internet unavailable retry strategy.
 *  
 */
public class HuaweiCloudCheckIn implements Runnable {

    private Logger logger = LoggerFactory.getLogger(HuaweiCloudCheckIn.class);
    private static String PASSWORD;
    private static String USERNAME;
    private static String KEY;
    //"C:\\Program Files (x86)\\Google\\chromedriver.exe"
    private static String CHROMEDRIVERPATH;


    public static final String SIGN_IN = "https://auth.huaweicloud.com/authui/login.html?service=https%3A%2F%2Fdevcloudsso.huaweicloud.com%2Fauthticket%3Fservice%3Dhttps%253A%252F%252Fdevcloud.huaweicloud.com%252Fbonususer%252Fhome%253Ffrom_region%253Dcn-north-1#/login";

    /**
     * args[0]: USERNAME
     * args[1]: Encrypted PASSWORD
     * args[2]: DES key
     * args[3]: Chrome Driver path
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        setParams(args);
        new Thread(new HuaweiCloudCheckIn()).start();
    }

    private static void setParams(String[] args) throws Exception {
        if (null == args || args.length != 4) {
            throw new RuntimeException("Need input parameters");
        }
        USERNAME = args[0];
        KEY = args[2];
        byte[] encryptedBytes = DESStringUtil.hexStr2ByteArr(args[1]);
        Arrays.stream(args).forEach(System.out::println);
        System.out.println("encryptedBytes.length:" + encryptedBytes.length);
        PASSWORD = new String(new DESStringUtil(KEY).decrypt(encryptedBytes));
        CHROMEDRIVERPATH = args[3];
    }

    @Override
    public void run() {
        if (signedIn()) {
            return;
        }
//        safeSleep();
        // 可省略，若驱动放在其他目录需指定驱动路径
        System.setProperty("webdriver.chrome.driver", CHROMEDRIVERPATH);
        ChromeOptions options = new ChromeOptions();
        //Run on back-end. https://www.jianshu.com/p/30b60f5da23c
        options.addArguments("headless");
        options.addArguments("no-sandbox");
        ChromeDriver driver = new ChromeDriver(options);
        try {
            driver.get(SIGN_IN);
            addRandomSleep(1000, 1 << 4);
            driver.findElement(By.xpath("//*[@type=\"text\"]")).sendKeys(USERNAME);
            addRandomSleep(2000, 1 << 4);
            //设置密码
            driver.findElement(By.xpath("//*[@type=\"password\"]")).sendKeys(PASSWORD);
            addRandomSleep(2000, 1 << 4);
            // 模拟点击登录   //form[@id='form-group-login']/button
            driver.findElement(By.id("btn_submit")).click();
            addRandomSleep(1000, 1 << 6);
            // 模拟点击签到   //form[@id='form-group-login']/button
            if (driver.findElements(By.xpath("//*[contains(text(),'已签到')]")).size() > 0) {
                logger.info("之前已签过到");
                markAsSignedIn();
                return;

            }
            driver.findElement(By.id("homeheader-signin")).click();
            //https://www.softwaretestinghelp.com/selenium-find-element-by-text/
            //org.openqa.selenium.NoSuchElementException: no such element: Unable to locate element: {"method":"xpath","selector":"//*[text()='已签到']"}
            //for the above exception, it needs a delay to load the response after a click.
            addRandomSleep(1000, 1 << 6);
            WebElement signedIn = driver.findElement(By.xpath("//*[contains(text(),'已签到')]"));
            if (signedIn != null) {
                logger.info("Success");
                markAsSignedIn();
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                addRandomSleep(10 * 1000, 1 << 4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.close();
        }
    }

    private void safeSleep() {
        try {
            Thread.sleep(5 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void markAsSignedIn() {
        logger.info("完成签到");
        File folder = new File("data");
        if (!folder.exists()) {
            folder.mkdir();
        }
        File file = new File("data/" +
                new SimpleDateFormat("yyyyMMdd").format(new Date()));
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean signedIn() {
        File file = new File("data/" +
                new SimpleDateFormat("yyyyMMdd").format(new Date()));
        if (file.exists()) {
            logger.info("已签到");
            return true;
        }
        return false;
    }

    private void addRandomSleep(int initialization, int bound) throws InterruptedException {
        // 添加随机延迟
        Thread.sleep(initialization + 100 * new Random().nextInt(bound));
    }
}
/**
 * org.openqa.selenium.WebDriverException: unknown error: Chrome failed to start: exited abnormally
 *
 * Starting ChromeDriver 2.41.578700 (2f1ed5f9343c13f73144538f15c00b370eda6706) on port 19501
 * Only local connections are allowed.
 * 
 * java.lang.IllegalAccessError: tried to access method com.google.common.util.concurrent.SimpleTimeLimiter.<init>(Ljava/util/concurrent/ExecutorService;)V from class org.openqa.selenium.net.UrlChecker
 * 
 * 
 * java.lang.IllegalAccessError: tried to access method com.google.common.util.concurrent.SimpleTimeLimiter.<init>(Ljava/util/concurrent/ExecutorService;)V from class org.openqa.selenium.net.UrlChecker
 *      the main problem is you have a jar conflict. https://stackoverflow.com/questions/45643956/illegalaccesserror-thrown-by-new-chromedriver-on-osx-java
 *      Checking the Compile Dependencies with the help of Maven repository
 *      
 * org.openqa.selenium.SessionNotCreatedException: session not created: This version of ChromeDriver only supports Chrome version 79
 *      https://chromedriver.chromium.org/downloads
 *      
 * timed out receiving message from renderer
 *      https://stackoverflow.com/questions/48450594/selenium-timed-out-receiving-message-from-renderer
 *      
 *      
 * Exception in thread "Thread-0" org.openqa.selenium.SessionNotCreatedException: session not created: This version of ChromeDriver only supports Chrome version 81
 * https://chromedriver.chromium.org/downloads
 */