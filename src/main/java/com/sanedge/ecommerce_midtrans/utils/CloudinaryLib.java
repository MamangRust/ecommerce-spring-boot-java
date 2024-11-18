package com.sanedge.ecommerce_midtrans.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class CloudinaryLib {
    private final Cloudinary cloudinary;

    public CloudinaryLib(
            @Value("${CLOUDINARY_CLOUD_NAME}") String cloudName,
            @Value("${CLOUDINARY_API_KEY}") String apiKey,
            @Value("${CLOUDINARY_SECRET_KEY}") String apiSecret) {

        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    public Cloudinary cloudinary() {
        return cloudinary;
    }
}
