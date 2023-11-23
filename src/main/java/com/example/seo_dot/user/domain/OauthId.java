package com.example.seo_dot.user.domain;

import com.example.seo_dot.user.domain.enums.Platform;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthId {
    @Column(nullable = false, name = "oauth_server_id")
    private String oauthServerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "platform")
    private Platform platform;

    @Override
    public String toString() {
        return "OauthId{" +
                "oauthServerId='" + oauthServerId + '\'' +
                ", platform=" + platform +
                '}';
    }
}
