package com.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnlineTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineTestApplication.class, args);
    }
}