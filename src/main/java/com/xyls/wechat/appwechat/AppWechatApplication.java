package com.xyls.wechat.appwechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class AppWechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppWechatApplication.class, args);
    }

}
