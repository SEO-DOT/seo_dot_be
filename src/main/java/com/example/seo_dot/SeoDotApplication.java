package com.example.seo_dot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SeoDotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeoDotApplication.class, args);
    }

}
