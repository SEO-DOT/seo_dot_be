package com.example.seo_dot.mypage.controller;

import com.example.seo_dot.book.dto.response.PageDto;
import com.example.seo_dot.global.dto.MessageResponseDTO;
import com.example.seo_dot.global.security.UserDetailsImpl;
import com.example.seo_dot.mypage.dto.request.MyPageOrderPageParam;
import com.example.seo_dot.mypage.dto.request.MyPageUserUpdateRequestDTO;
import com.example.seo_dot.mypage.dto.response.MyPageReviewResponseDTO;
import com.example.seo_dot.mypage.dto.response.MyPageUserDetailResponseDTO;
import com.example.seo_dot.mypage.dto.response.MyPageUserInfoResponseDTO;
import com.example.seo_dot.mypage.dto.response.MyPageUserOrderListResponseDTO;
import com.example.seo_dot.mypage.service.MyPageService;
import com.example.seo_dot.review.dto.request.ReviewPageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/api/mypage/user")
    public ResponseEntity<MyPageUserInfoResponseDTO> getMyPageUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        MyPageUserInfoResponseDTO response = myPageService.getMyPageUserInfo(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/api/mypage/reviews")
    public ResponseEntity<PageDto<List<MyPageReviewResponseDTO>>> getMyPageReviews(@ModelAttribute ReviewPageParam pageParam,
                                                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Slice<MyPageReviewResponseDTO> result = myPageService.getMyPageReviews(pageParam, userDetails.getUser());
        PageDto<List<MyPageReviewResponseDTO>> response = new PageDto(result.getContent(), result.hasNext());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/api/mypage/user-detail")
    public ResponseEntity<MyPageUserDetailResponseDTO> getUserDetail(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        MyPageUserDetailResponseDTO response = myPageService.getUserDetail(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/api/mypage/orders")
    public ResponseEntity<PageDto<List<MyPageUserOrderListResponseDTO>>> getUserOrders(@ModelAttribute MyPageOrderPageParam pageParam, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Slice<MyPageUserOrderListResponseDTO> result = myPageService.getUserOrders(pageParam, userDetails.getUser());
        PageDto<List<MyPageUserOrderListResponseDTO>> response = new PageDto<>(result.getContent(), result.hasNext());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/api/mypage/profile")
    public ResponseEntity<MessageResponseDTO> updateProfile(@RequestPart(value = "profile-image") MultipartFile multipartFile,
                                                            @RequestParam("nickname") String nickname,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MessageResponseDTO response = myPageService.updateProfile(multipartFile, nickname, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/api/mypage/user")
    public ResponseEntity<MessageResponseDTO> updateUserInfo(@RequestBody MyPageUserUpdateRequestDTO requestDTO,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MessageResponseDTO response = myPageService.updateUserInfo(requestDTO, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
