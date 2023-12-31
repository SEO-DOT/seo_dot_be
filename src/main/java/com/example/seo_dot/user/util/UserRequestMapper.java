package com.example.seo_dot.user.util;

import com.example.seo_dot.user.domain.dto.UserDto;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserRequestMapper {
    public UserDto toDto(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        return UserDto.builder()
                .email((String)attributes.get("email"))
                .name((String)attributes.get("name"))
                .picture((String)attributes.get("picture"))
                .build();
    }
}
