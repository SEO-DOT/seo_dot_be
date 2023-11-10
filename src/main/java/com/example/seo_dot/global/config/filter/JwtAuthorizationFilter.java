package com.example.seo_dot.global.config.filter;

import com.example.seo_dot.global.jwt.JwtProvider;
import com.example.seo_dot.global.security.UserDetailsServiceImpl;
import com.example.seo_dot.user.domain.dto.UserDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j(topic = "AuthenticationFilter")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtProvider.getTokenFromHeader(request);
        log.info("tokenValue={}", tokenValue);

        if (StringUtils.hasText(tokenValue)) {
            tokenValue = jwtProvider.substringToken(tokenValue);

            if (!jwtProvider.validateToken(tokenValue)) {
                log.error("Token Error");
                return;
            }

            Claims info = jwtProvider.getUserInfoFromToken(tokenValue);

            System.out.println("subject : " + info.getSubject());

            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


//    public Authentication getAuthentication(UserDto userDto) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getEmail());
//        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        return new UsernamePasswordAuthenticationToken(member, "",
//                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
//    }
    public void setAuthentication(String subject) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(subject);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String subject) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }
}
