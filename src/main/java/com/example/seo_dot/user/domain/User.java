package com.example.seo_dot.user.domain;

import com.example.seo_dot.user.domain.enums.Gender;
import com.example.seo_dot.user.domain.enums.UserRoleEnum;
import com.example.seo_dot.user.model.KakaoUserInfoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String username;

    @Column(nullable = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    private String profileImage;

    private int point;

    private String phoneNumber;

    @Embedded
    private Address address;

    @Embedded
    private OauthId oauthId;

    private Gender gender;
    private boolean activated;
    private boolean deleted;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public User(String username, String email, UserRoleEnum role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public User(KakaoUserInfoDto kakaoUserInfo, OauthId oauthid) {
        this.email = kakaoUserInfo.getEmail();
        this.role = UserRoleEnum.USER;
        this.oauthId = oauthid;
    }

    public void updateUserProfile(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public void updateUserInfo(String username, Address address, String phoneNumber) {
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}