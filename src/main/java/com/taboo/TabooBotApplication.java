package com.taboo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TabooBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(TabooBotApplication.class);
    }
}
