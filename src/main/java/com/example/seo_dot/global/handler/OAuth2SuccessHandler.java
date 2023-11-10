package com.example.seo_dot.global.handler;

import com.example.seo_dot.global.jwt.JwtProvider;
import com.example.seo_dot.global.jwt.Token;
import com.example.seo_dot.user.domain.dto.UserDto;
import com.example.seo_dot.user.domain.enums.Role;
import com.example.seo_dot.user.util.UserRequestMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;
//    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserDto userDto = userRequestMapper.toDto(oAuth2User);


        log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);
        // 최초 로그인이라면 회원가입 처리를 한다.
        String targetUrl;
        log.info("토큰 발행 시작");

        Token token = jwtProvider.generateToken(authentication.getName(), Role.USER);
        log.info("{}", token);

        writeTokenResponse(response, token);
//        targetUrl = UriComponentsBuilder.fromUriString("/home")
//                .queryParam("token", "token")
//                .build().toUriString();
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);

//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setCharacterEncoding("UTF-8");
//        Map<String, String> stringStringMap = Collections.singletonMap("auth", "auth");
//        objectMapper.writeValue(response.getWriter(), stringStringMap);;
//        getRedirectStrategy().sendRedirect(request,response,"http://localhost:3000/oauth/redirected/kakao");
        getRedirectStrategy().sendRedirect(request,response,"http://localhost:3000/hello");
    }

    private void writeTokenResponse(HttpServletResponse response, Token token)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("AccessToken", token.getAccessToken());
        response.addHeader("RefreshToken", token.getRefreshToken());

        response.setContentType("application/json;charset=UTF-8");

//        var writer = response.getWriter();
//        writer.println(objectMapper.writeValueAsString(token));
//        writer.flush();
    }
}
