package com.example.seo_dot.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageValidator {

    private final Tika tika = new Tika();

    public void validateImageFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("이미지가 존재하지 않습니다.");
        }
        if (!isImageFile(multipartFile)) {
            throw new IllegalArgumentException("이미지 파일이 아닙니다.");
        }
    }

    private boolean isImageFile(MultipartFile multipartFile) {
        try {
            String mimeType = tika.detect(multipartFile.getInputStream());
            if (!mimeType.startsWith("image")) {
                return false;
            }
        } catch (IOException e) {
            log.error("이미지 저장 실패 {}", e);
            throw new RuntimeException("이미지 저장에 실패하였습니다.", e);
        }
        return true;
    }
}
