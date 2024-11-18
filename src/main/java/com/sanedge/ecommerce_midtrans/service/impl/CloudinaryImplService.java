package com.sanedge.ecommerce_midtrans.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sanedge.ecommerce_midtrans.service.CloudinaryService;
import com.sanedge.ecommerce_midtrans.utils.CloudinaryLib;

@Service
public class CloudinaryImplService implements CloudinaryService {
    private final Cloudinary cloudinary;  

    
    @Autowired
    public CloudinaryImplService(CloudinaryLib cloudinaryLib) {
        this.cloudinary = cloudinaryLib.cloudinary();  
    }

   
    public String uploadToCloudinary(MultipartFile file, String filePath) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }

        
        @SuppressWarnings("unchecked")
        Map<String, Object> uploadParams = ObjectUtils.asMap(
            "public_id", filePath, 
            "resource_type", "image"
        );

        
        @SuppressWarnings("unchecked")
        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadParams);

        
        return (String) uploadResult.get("secure_url");
    }
}