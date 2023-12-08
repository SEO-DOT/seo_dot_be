package com.example.seo_dot.user.controller;

import com.example.seo_dot.global.jwt.JwtUtil;
import com.example.seo_dot.global.jwt.Token;
import com.example.seo_dot.global.security.UserDetailsImpl;
import com.example.seo_dot.user.domain.dto.NicknameRequestDto;
import com.example.seo_dot.user.model.SignupInfoRequestDto;
import com.example.seo_dot.user.service.KakaoService;
import com.example.seo_dot.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final KakaoService kakaoService;
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

    @PostMapping("/api/user/signup/info")
    public ResponseEntity signup(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody SignupInfoRequestDto signupInfoRequestDto) {
        userService.createsignupInfo(userDetails, signupInfoRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/user/nickname")
    public ResponseEntity validateNickname(@RequestBody NicknameRequestDto nicknameRequestDto) {
        userService.validateNickname(nicknameRequestDto);
        return ResponseEntity.ok().build();
    }
}
