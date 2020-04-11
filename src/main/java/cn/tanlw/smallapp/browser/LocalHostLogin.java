package cn.tanlw.smallapp.browser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;

public class LocalHostLogin implements Runnable {

    public static final String SIGN_IN = "http://localhost/#/login";

    public static void main(String[] args){
        new Thread(new LocalHostLogin()).start();
    }

    @Override
    public void run() {
        // 可省略，若驱动放在其他目录需指定驱动路径
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        try {
            driver.get(SIGN_IN);
            addRandomSleep(1000, 1<<4);
            WebElement signedIn = driver.findElement(By.xpath("//*[text()='登录您的账户']"));
            if (signedIn != null) {
                System.out.println("Success!");
            }
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            try {
                Thread.sleep(15 * 1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

    private void addRandomSleep(int initialization, int bound) throws InterruptedException {
        // 添加随机延迟
        Thread.sleep(initialization + 100 * new Random().nextInt(bound));
    }
}
