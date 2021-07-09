package com.marb.zupcomics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ZupComicsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZupComicsApplication.class, args);
    }

}
