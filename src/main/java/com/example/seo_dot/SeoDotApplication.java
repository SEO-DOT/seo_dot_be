package com.example.seo_dot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SeoDotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeoDotApplication.class, args);
    }

}
