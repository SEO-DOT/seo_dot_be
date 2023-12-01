package com.example.seo_dot.global.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

    String storeImage(MultipartFile multipartFile, ImageFolder imageFolder);

    void deleteImage(String image);

    String getDefaultProfileImage();
}
