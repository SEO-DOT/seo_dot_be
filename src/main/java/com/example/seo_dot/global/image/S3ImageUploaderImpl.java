package com.example.seo_dot.global.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.seo_dot.mypage.service.ImageValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3ImageUploaderImpl implements ImageUploader {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    private final ImageValidator imageValidator;

    @Value("${default.profile.image.url}")
    private String defaultProfileImageUrl;

    @Override
    public String storeImage(MultipartFile multipartFile, ImageFolder imageFolder) {

        imageValidator.validateImageFile(multipartFile);

        // 기존의 파일명
        String originalFileName = multipartFile.getOriginalFilename();

        // 저장될 파일명
        String storeFileName = createStoreFileName(originalFileName, imageFolder);

        ObjectMetadata objectMetadata = createObjectMetadata(multipartFile);
        try {
            InputStream inputStream = multipartFile.getInputStream();
            amazonS3.putObject(new PutObjectRequest(bucket, storeFileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return amazonS3.getUrl(bucket, storeFileName).toString();
    }

//    @Async
    @Override
    public void deleteImage(String image) {
        try {
            log.info("imageName = {}", image);
            String deleteFileName = createDeleteFileName(image);
            amazonS3.deleteObject(bucket, deleteFileName);
        } catch (RuntimeException e) {
            log.error("삭제 실패 파일명 = {}", image, e);
        }
    }

    @Override
    public String getDefaultProfileImage() {
        return this.defaultProfileImageUrl;
    }

    private String createStoreFileName(String originalFileName, ImageFolder imageFolder) {
        String ext = extractExt(originalFileName);
        String fileName = extractFileName(originalFileName);
        String folderName = imageFolder.getFolderName();
//        String fileSeparator = File.separator;
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
        String uuid = UUID.randomUUID().toString();
        log.info("fileName = {} ", fileName);
        return folderName + "/" + nowTime + fileName + uuid + "." + ext;
    }


    private String createDeleteFileName(String image){
        int startIndex = image.indexOf(".com/") + 5;
        String extractedString = image.substring(startIndex);
        String deleteFileName = URLDecoder.decode(extractedString, StandardCharsets.UTF_8).toString();
        log.info("extractedString = {}" , deleteFileName);
        return deleteFileName;
    }

    private ObjectMetadata createObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        return objectMetadata;
    }

    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        String ext = originalFileName.substring(pos + 1);
        return ext;
    }

    private String extractFileName(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        String fileName = originalFileName.substring(0, pos);
        return fileName;
    }
}
