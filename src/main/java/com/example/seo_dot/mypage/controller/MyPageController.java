package com.example.seo_dot.mypage.controller;

import com.example.seo_dot.book.dto.response.PageDto;
import com.example.seo_dot.global.dto.MessageResponseDTO;
import com.example.seo_dot.mypage.service.MyPageService;
import com.example.seo_dot.mypage.dto.response.MyPageReviewResponseDTO;
import com.example.seo_dot.mypage.dto.response.MyPageUserInfoResponseDTO;
import com.example.seo_dot.review.dto.request.ReviewPageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/api/mypage/user")
    public ResponseEntity<MyPageUserInfoResponseDTO> getMyPageUserInfo(Long userId) {
        MyPageUserInfoResponseDTO response = myPageService.getMyPageUserInfo(1L);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/api/mypage/reviews")
    public ResponseEntity<PageDto<List<MyPageReviewResponseDTO>>> getMyPageReviews(@ModelAttribute ReviewPageParam pageParam, Long userId) {
        Slice<MyPageReviewResponseDTO> result = myPageService.getMyPageReviews(pageParam,1L);
        PageDto<List<MyPageReviewResponseDTO>> response = new PageDto(result.getContent(), result.hasNext());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/api/mypage/profile-image")
    public ResponseEntity<MessageResponseDTO> updateProfileImage(@RequestPart(value = "profile-image") MultipartFile multipartFile,
                                                                 @RequestParam("nickname") String nickname,
                                                                 Long userId) {
        MessageResponseDTO response = myPageService.updateProfileImage(multipartFile, nickname,  1L);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
