package com.example.seo_dot.global.config.filter;

import com.example.seo_dot.global.jwt.JwtUtil;
import com.example.seo_dot.global.security.UserDetailsServiceImpl;
import com.example.seo_dot.user.domain.OauthId;
import com.example.seo_dot.user.domain.enums.Platform;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getJwtFromHeader(req);

        if (StringUtils.hasText(tokenValue)) {

            if (!jwtUtil.validateToken(tokenValue)) {
                log.error("Token Error");
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
            if (info.get("OauthId") != null) {
                OauthId oauthId = convertToObject(info.get("OauthId"));
                try {
                    setAuthentication(oauthId);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return;
                }
            }
        }

        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(OauthId oauthId) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(oauthId);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(OauthId oauthId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(oauthId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private OauthId convertToObject(Object inputObject) {
        if (inputObject instanceof Map) {
            Map<?, ?> inputMap = (Map<?, ?>) inputObject;

            // Assuming that "oauthServerId" and "platform" keys exist in the map
            String oauthServerId = String.valueOf(inputMap.get("oauthServerId"));
            Platform platform = Platform.valueOf(String.valueOf(inputMap.get("platform")));

            OauthId oauthId = new OauthId(oauthServerId, platform);

            return oauthId;
        }
        return null;
    }
}