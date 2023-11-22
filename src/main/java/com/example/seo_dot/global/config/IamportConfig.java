package com.example.seo_dot.global.config;

import com.siot.IamportRestClient.IamportClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IamportConfig {

    @Value("${iamport.key}")
    private String apiKey;
    @Value("${iamport.secret}")
    private String apiSecretKey;

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, apiSecretKey);
    }


}
