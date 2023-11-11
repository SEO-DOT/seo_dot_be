package com.example.seo_dot.global.auth;

import com.example.seo_dot.user.domain.User;
import com.example.seo_dot.user.domain.enums.Platform;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String name;
    private String email;
    private String picture;
    private Platform platform;

    // 해당 로그인인 서비스가 kakao인지 google인지 구분하여, 알맞게 매핑을 해주도록 합니다.
    // 여기서 registrationId는 OAuth2 로그인을 처리한 서비스 명("google","kakao","naver"..)이 되고,
    // userNameAttributeName은 해당 서비스의 map의 키값이 되는 값이됩니다. {google="sub", kakao="id", naver="response"}
    public static OAuthAttributes of(String provider, String attributeKey, Map<String, Object> attributes) {
        switch (provider) {
            case "google":
                return ofGoogle(attributeKey, attributes);
            case "kakao":
                return ofKakao(attributeKey, attributes);
            default:
                throw new RuntimeException();
        }
    }

    private static OAuthAttributes ofKakao(String attributeKey,
                                           Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String)kakaoProfile.get("profile_image_url"))
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .platform(Platform.KAKAO)
                .build();
    }
    private static OAuthAttributes ofGoogle(String attributeKey,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .platform(Platform.GOOGLE)
                .build();
    }

    // .. getter/setter 생략

    Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("name", name);
        map.put("email", email);
        map.put("picture", picture);
        map.put("platform", platform);
        return map;
    }

    User toEntity() {
        return new User(attributeKey, email, name, platform);
    }
}
