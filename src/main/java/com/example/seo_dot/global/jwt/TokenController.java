package com.example.seo_dot.global.jwt;

import com.example.seo_dot.user.domain.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final JwtProvider jwtProvider;

    @GetMapping("/token/expired")
    public String auth() {
        throw new RuntimeException();
    }

    @GetMapping("/token/refresh")
    public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("RefreshToken");

        if (token != null) {
            String subject = jwtProvider.getUserInfoFromToken(token).getSubject();
            jwtProvider.validateRefreshToken(subject);
            Token newToken = jwtProvider.generateToken(subject, Role.USER);

            response.addHeader("AccessToken", newToken.getAccessToken());
            response.addHeader("RefreshToken", newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return "HAPPY NEW TOKEN";
        }

        throw new RuntimeException();
    }
}
