package com.example.seo_dot.user.controller;

import com.example.seo_dot.bookmark.model.NicknameRequestDto;
import com.example.seo_dot.global.jwt.Token;
import com.example.seo_dot.global.security.UserDetailsImpl;
import com.example.seo_dot.user.domain.dto.SignupRequestDto;
import com.example.seo_dot.user.model.SignupInfoRequestDto;
import com.example.seo_dot.user.service.GoogleService;
import com.example.seo_dot.user.service.KakaoService;
import com.example.seo_dot.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final KakaoService kakaoService;
    private final GoogleService googleService;
    private final UserService userService;

    @GetMapping("/test")
    public String getUser() {
        return "Hello World";
    }

    @GetMapping("/api/user/refresh")
    public ResponseEntity<Map<String, String>> refreshAccessToken(HttpServletRequest request) {

        String accessToken = userService.tokenRefresh(request);
        Map<String, String> stringStringMap = Collections.singletonMap("AccessToken", accessToken);


        return ResponseEntity.ok(stringStringMap);
    }

    @GetMapping("/api/user/kakao/callback")
    public ResponseEntity<Token> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        Token token =  kakaoService.kakaoLogin(code);

        ResponseEntity<Token> tokens = ResponseEntity.ok().body(token);
        log.info("response={}", tokens.getBody().getAccessToken());
        return tokens;
    }

    @GetMapping("/api/user/google/callback")
    public ResponseEntity<Token> googleLogin(@RequestParam String code) throws JsonProcessingException {
        Token token =  googleService.googleLogin(code);

        ResponseEntity<Token> tokens = ResponseEntity.ok().body(token);
        log.info("response={}", tokens.getBody().getAccessToken());
        return tokens;
    }

    @PostMapping("/api/user/signup/info")
    public ResponseEntity signup(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody SignupInfoRequestDto signupInfoRequestDto) {
        userService.createsignupInfo(userDetails, signupInfoRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/user/nickname")
    public ResponseEntity validateNickname(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody NicknameRequestDto nicknameRequestDto) {
        userService.validateNickname(userDetails, nicknameRequestDto);
        return ResponseEntity.ok().build();
    }
}
