//package com.example.seo_dot.global.config.filter;
//
//import com.example.seo_dot.global.jwt.JwtUtil;
//import com.example.seo_dot.global.security.UserDetailsImpl;
//import com.example.seo_dot.user.domain.dto.LoginRequestDto;
//import com.example.seo_dot.user.domain.enums.UserRoleEnum;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.IOException;
//
//@Slf4j(topic = "로그인 및 JWT 생성")
//public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    private final JwtUtil jwtUtil;
//
//    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//        setFilterProcessesUrl("/api/user/login");
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
//            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
//
//            return getAuthenticationManager().authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            requestDto.getUsername(),
//                            requestDto.getPassword(),
//                            null
//                    )
//            );
//        } catch (IOException e) {
//            log.error(e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
//        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
//        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
//
//        String accessToken = jwtUtil.createAccessToken(username, role);
//        String refreshToken = jwtUtil.createRefreshToken(username, role);
//        response.addHeader(JwtUtil.AUTHORIZATION_ACCESS_HEADER, accessToken);
//        response.addHeader(JwtUtil.AUTHORIZATION_REFRESH_HEADER, refreshToken);
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
//        response.setStatus(401);
//    }
//
//}