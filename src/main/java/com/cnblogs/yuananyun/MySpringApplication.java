package com.cnblogs.yuananyun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @create 2018/7/5
 */
@SpringBootApplication(scanBasePackages = "com.cnblogs")
public class MySpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(MySpringApplication.class,args);
    }
}
