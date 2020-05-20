package com.github.binarywang.utils.qrcode;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 成就客户、专业之上、持续创新、诚信敬业、合作共赢
 * 成为一家世界级的数字商业云服务厂商
 *
 * @Author: xingyan,
 * @Date: 2020/5/20
 * @DESC:
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) {

// SpringApplication.run(Application.class, args);

        SpringApplication application = new SpringApplication(App.class);

        application.setBannerMode(Banner.Mode.OFF);

        application.run(args);

    }

}
