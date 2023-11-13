package com.example.seo_dot.user.controller;

import com.example.seo_dot.global.jwt.JwtUtil;
import com.example.seo_dot.global.jwt.Token;
import com.example.seo_dot.user.service.KakaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final KakaoService kakaoService;

    @GetMapping("/test")
    public String getUser() {
        return "Hello World";
    }

    @GetMapping("/user/kakao/callback")
    public ResponseEntity<Token> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        Token token =  kakaoService.kakaoLogin(code);

        ResponseEntity<Token> tokens = ResponseEntity.ok().body(token);
        log.info("response={}", tokens.getBody().getAccessToken());
        return tokens;
    }
}
