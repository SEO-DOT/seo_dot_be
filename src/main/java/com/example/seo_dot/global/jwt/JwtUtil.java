package com.example.seo_dot.global.jwt;

import com.example.seo_dot.user.domain.OauthId;
import com.example.seo_dot.user.domain.User;
import com.example.seo_dot.user.domain.enums.UserRoleEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "JwtUtil")
@RequiredArgsConstructor
@Component
public class JwtUtil {
    // Access token Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    private final RedisTemplate<String, String> redisTemplate;
    // 토큰 만료시간
    @Value("${jwt.token.access-expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${jwt.token.refresh-expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION_TIME;

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // accessToken 생성
    public String createAccessToken(User user) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(user.getEmail()) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, user.getRole()) // 사용자 권한
                        .claim("OauthId", user.getOauthId())
                        .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_EXPIRATION_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    public String createRefreshToken(User user) {
        Date date = new Date();

        String refreshToken =
                BEARER_PREFIX + Jwts.builder()
                        .setSubject(user.getEmail()) // 사용자 식별자값(ID)
                        .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_EXPIRATION_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();

        saveRefreshTokenToRedis(refreshToken, user.getOauthId());

        return refreshToken;
    }

    private void saveRefreshTokenToRedis(String refreshToken, OauthId oauthId) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(oauthId);
            String substring = refreshToken.substring(BEARER_PREFIX.length());
            redisTemplate.opsForValue().set(substring,
                    jsonString,
                    REFRESH_TOKEN_EXPIRATION_TIME,
                    TimeUnit.MILLISECONDS
            );
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
    // header 에서 JWT 가져오기
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
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

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        log.info("Jwt body={}", Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public void tokenAddCookie(HttpServletResponse response, String headerName, String jwt) {
        String encodeJwt = URLEncoder.encode(jwt, StandardCharsets.UTF_8);
//        ResponseCookie responseCookie = ResponseCookie.from(headerName, encodeJwt)
//                .path("/")
//                .secure(true)
//                .sameSite("None")
//                .maxAge(60 * 60 * 24)
//                .build();
//
//        response.setHeader("Set-Cookie", responseCookie.toString());

        Cookie cookie = new Cookie(headerName, encodeJwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge(60 * 60 * 24);

//        response.addHeader("Access-Control-Allow-Credentials", "true");
//        response.addHeader("Access-Control-Allow-Origin", "*");

        response.addCookie(cookie);
    }

    public void refreshTokenCheck(String refreshToken) {
        if (redisTemplate.opsForValue().get(refreshToken) == null) {
            throw new IllegalArgumentException("유효한 RefreshToken 이 아닙니다.");
        }
        if (!validateToken(refreshToken)) {
            log.error("Token Error");
        }
    }

    public OauthId getOauthIdFromRedis(String refreshToken) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String oauthIdJsonString = redisTemplate.opsForValue().get(refreshToken);

            return mapper.readValue(oauthIdJsonString, OauthId.class);
        } catch(JsonProcessingException e) {
            log.info(e.getMessage());
        }
        return null;
    }
}