package com.sanedge.ecommerce_midtrans.utils;

import com.cloudinary.Cloudinary;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class CloudinaryLib {
    private final Cloudinary cloudinary;

    public CloudinaryLib(
            @Value("${cloudinary.cloud.name}") String cloudName,
            @Value("${cloudinary.api.key}") String apiKey,
            @Value("${cloudinary.secret.key}") String apiSecret) {
        
        Map<String, String> config = new HashMap<String, String>();

        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);


        cloudinary = new Cloudinary(config);
    }

    public Cloudinary cloudinary() {
        return cloudinary;
    }
}
