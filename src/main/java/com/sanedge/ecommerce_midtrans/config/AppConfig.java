package com.sanedge.ecommerce_midtrans.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.sanedge.ecommerce_midtrans.utils.CloudinaryLib;

@Configuration
public class AppConfig {
    @Value("${cloudinary.cloud.name}")
    private String cloudName;

    @Value("${cloudinary.api.key}")
    private String apiKey;

    @Value("${cloudinary.secret.key}")
    private String apiSecret;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CloudinaryLib cloudinaryLib() {
        return new CloudinaryLib(cloudName, apiKey, apiSecret);
    }
}