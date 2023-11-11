package com.example.seo_dot.global.jwt;

import com.example.seo_dot.user.domain.enums.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "JwtProvider")
public class JwtProvider {
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${spring.jwt.secret.key}")
    private String secretKey;

    @Value("${spring.jwt.token.access-expiration-time}")
    private long accessExpirationTime;

    @Value("${spring.jwt.token.refresh-expiration-time}")
    private long refreshExpirationTime;

    // Header KEY 값
    // 사용자 권한 값의 KEY
    public static final String ACCESS_TOKEN_HEADER = "AccessToken";
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public Token generateToken(String uid, Role role) {

        String accessToken = createToken(uid, accessExpirationTime);
        String refreshToken = createToken(uid, refreshExpirationTime);

        saveRefreshTokenToRedis(uid, refreshToken);

        return new Token(accessToken, refreshToken);
    }

    public String createToken(String uid, long expirationTime) {

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expirationTime);

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(uid)
                        .claim(AUTHORIZATION_KEY, Role.USER)
                        .setIssuedAt(now)
                        .setExpiration(expireDate)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    private void saveRefreshTokenToRedis(String name, String token) {
        redisTemplate.opsForValue().set(
                name,
                token,
                refreshExpirationTime,
                TimeUnit.SECONDS
        );
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public void addJwtToHttpHeader(String token, HttpServletResponse response) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");
            response.setHeader(ACCESS_TOKEN_HEADER, token);
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
        }
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        log.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    public String getTokenFromHeader(HttpServletRequest req) {
        String token = req.getHeader(ACCESS_TOKEN_HEADER);
        if (token != null) {
            return tokenDecoder(token);
        }
        token = req.getHeader(REFRESH_TOKEN_HEADER);
        if (token != null) {
            return tokenDecoder(token);
        }
        return null;
    }

    private String tokenDecoder(String token) {
        try {
            return URLDecoder.decode(token, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public void validateRefreshToken(String subject) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        if (opsForValue.get(subject) != null) {
            return;
        }
        throw new IllegalArgumentException("유효한 refresh token 이 아닙니다.");
    }
}
