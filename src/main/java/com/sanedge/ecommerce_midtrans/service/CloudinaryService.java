package com.sanedge.ecommerce_midtrans.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    public String uploadToCloudinary(MultipartFile file, String filePath) throws IOException;
}
