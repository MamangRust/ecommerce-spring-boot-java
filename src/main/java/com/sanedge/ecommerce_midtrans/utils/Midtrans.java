package com.sanedge.ecommerce_midtrans.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.service.MidtransSnapApi;

@Configuration
public class Midtrans {
    @Value("${midtrans.server-key}")
    private String serverKey;

    @Value("${midtrans.client-key}")
    private String clientKey;

    @Value("${midtrans.environment}")
    private String environment;

    @Bean
    public ConfigFactory midtransConfig() {
        ConfigFactory config = new ConfigFactory(Config.builder().setServerKey(serverKey).setClientKey(clientKey).setIsProduction(false).build());

        return config;
    }

    public MidtransSnapApi mySnapApi(){
        return this.midtransConfig().getSnapApi();
    }
}
