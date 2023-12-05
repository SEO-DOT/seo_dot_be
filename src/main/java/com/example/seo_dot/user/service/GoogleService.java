package com.example.seo_dot.user.service;

import com.example.seo_dot.global.jwt.JwtUtil;
import com.example.seo_dot.global.jwt.Token;
import com.example.seo_dot.user.domain.OauthId;
import com.example.seo_dot.user.domain.User;
import com.example.seo_dot.user.domain.enums.Platform;
import com.example.seo_dot.user.model.GoogleUserInfoDto;
import com.example.seo_dot.user.model.KakaoUserInfoDto;
import com.example.seo_dot.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoogleService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public Token googleLogin(String code) throws JsonProcessingException {

        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        GoogleUserInfoDto googleUserInfo = getGoogleUserInfo(accessToken);

        // 3. 필요 시, 회원가입
        User googleUser = registerGoogleUserIfNeeded(googleUserInfo);

        // 4. JWT 토큰 반환
        String jwtAccessToken = jwtUtil.createAccessToken(googleUser);
        String jwtRefreshToken = jwtUtil.createRefreshToken(googleUser);

        Token token = new Token(jwtAccessToken,jwtRefreshToken);

        return token;
    }

    private String getToken(String code) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://oauth2.googleapis.com/token")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "1054291195131-b3p7obbg2naa8vlgevghrvvlfqlgq6rq.apps.googleusercontent.com");
        body.add("redirect_uri", "http://localhost:3000/auth/googlecallback");
        body.add("code", code);
        body.add("client_secret","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");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    private GoogleUserInfoDto getGoogleUserInfo(String accessToken) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder        // 요청 URL 만들기

                .fromUriString("https://www.googleapis.com/oauth2/v2/userinfo")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        String id = jsonNode.get("id").asText();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("user_account")
                .get("email").asText();

        return new GoogleUserInfoDto(id, nickname, email);
    }

    private User registerGoogleUserIfNeeded(GoogleUserInfoDto googleUserInfo) {

        String googleId = googleUserInfo.getId();
        OauthId oauthId = new OauthId(googleId, Platform.GOOGLE);
        User googleUser = userRepository.findByOauthId(oauthId).orElse(null);

        if (googleUser == null) {
            // 신규 회원가입
            googleUser = new User(googleUserInfo, oauthId);

            userRepository.save(googleUser);
        }
        return googleUser;
    }


//    @Value("${google.login.callback.url}")
//    private String googleCallbackUrl;
//
//    @Value("${google.login.client.id}")
//    private String googleLoginClientId;
//
//    @Value("${google.api.secret-key}")
//    private String googleApiSecretKey;
//
//    private static final String GOOGLE_USER_NICKNAME_PREFIX = "구글유저";
//
//
//    public User googleSignUpOrLinkUser (String code) throws JsonProcessingException {
//        log.info("구글 로그인 시도 중. 인증 코드: {}", code);
//
//        String accessToken = getGoogleToken(code);
//        log.info("구글 서버에서 토큰 받기 성공적. 액세스 토큰: {}", accessToken);
//
//        GoogleUserInfoDto googleUserInfo = getGoogleUserInfo(accessToken);
//        log.info("구글 사용자 정보: id={}, nickname={}, email={}, profileImageUrl={}",
//                googleUserInfo.getSub(), googleUserInfo.getName(), googleUserInfo.getEmail(), googleUserInfo.getPicture());
//
//        User user = userRepository.findByEmail(googleUserInfo.getEmail()).orElse(null);
//        if (user == null) {
//            log.info("새로운 구글 사용자 등록을 진행합니다.");
//            user = registerGoogleUser(googleUserInfo);
//        } else {
//            log.info("기존 사용자와 연결되는 구글 사용자로 등록합니다.");
//            // 구글 사용자와 기존 사용자를 연결하거나 필요한 정보를 업데이트하는 로직 추가 (예: 프로필 이미지 URL 업데이트 등)
//            user.setNickname(googleUserInfo.getName());
//            user.setProfileImageUrl(googleUserInfo.getPicture());
//        }
//        return user;
//    }
//
//    private GoogleUserInfoDto getGoogleUserInfo(String accessToken) throws JsonProcessingException {
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://www.googleapis.com/oauth2/v3/userinfo")
//                .queryParam("access_token", accessToken)
//                .encode()
//                .build()
//                .toUri();
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                uri,
//                HttpMethod.GET,
//                null,
//                String.class
//        );
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                return objectMapper.readValue(response.getBody(), GoogleUserInfo.class);
//            } catch (JsonProcessingException e) {
//                log.error("구글 API 응답 파싱 오류.", e);
//                throw new RuntimeException("구글 API 응답 파싱 오류.", e);
//            }
//        } else {
//            log.error("구글 인증 코드로 사용자 정보를 가져오는 중 오류 발생. 응답: {}", response.getBody());
//            throw new RuntimeException("구글 인증 코드로 사용자 정보를 가져오는 중 오류 발생.");
//        }
//    }
//    @Transactional
//    public User registerGoogleUser(GoogleUserInfoDto googleUserInfo) {
//        User user = userRepository.findByEmail(googleUserInfo.getEmail()).orElse(null);
//        if (user == null) {
//            // 사용자가 존재하지 않으면 새로운 사용자를 생성합니다.
//            String randomPwd = passwordEncoder.encode(String.valueOf(googleUserInfo.getSub()));
//            String username = googleUserInfo.getName();
//            String nickname = GOOGLE_USER_NICKNAME_PREFIX + googleUserInfo.getSub(); // Create a default nickname
//            // Set the default location
//            user = User.builder()
//                    .email(googleUserInfo.getEmail())
//                    .nickname(nickname)
//                    .password(randomPwd)
//                    .profileImageUrl(googleUserInfo.getPicture())
//                    .role(UserRoleEnum.USER)
//                    .username(username)
//                    .build();
//            userRepository.save(user);
//
//        } return user;
//    }
//
//    public String getGoogleToken (String code){
//        // 구글 인증 서버에 인증 코드를 전달하여 액세스 토큰을 받아온다.
//        String googleTokenUrl = "https://oauth2.googleapis.com/token";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("code", code);
//        map.add("client_id", googleLoginClientId);
//        map.add("client_secret", googleApiSecretKey);
//        map.add("redirect_uri", googleCallbackUrl);
//        map.add("grant_type", "authorization_code");
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(googleTokenUrl, request, String.class);
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                JsonNode jsonNode = objectMapper.readTree(response.getBody());
//                return jsonNode.get("access_token").asText();
//            } catch (JsonProcessingException e) {
//                log.error("구글 API 응답 파싱 오류.", e);
//                throw new RuntimeException("구글 API 응답 파싱 오류.", e);
//            }
//        } else {
//            log.error("구글 인증 코드로 액세스 토큰을 가져오는 중 오류 발생. 응답: {}", response.getBody());
//            throw new RuntimeException("구글 인증 코드로 액세스 토큰을 가져오는 중 오류 발생.");
//        }
//    }
//
}