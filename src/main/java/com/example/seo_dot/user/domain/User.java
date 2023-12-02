package com.example.seo_dot.user.domain;

import com.example.seo_dot.user.domain.dto.SignupRequestDto;
import com.example.seo_dot.user.domain.enums.Gender;
import com.example.seo_dot.user.domain.enums.UserRoleEnum;
import com.example.seo_dot.user.model.KakaoUserInfoDto;
import com.example.seo_dot.user.model.SignupInfoRequestDto;
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

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private String postNumber;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Embedded
    private Address address;

    @Embedded
    private OauthId oauthId;

    private Gender gender;
    private String age;
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

    public void updateSignupInfo(SignupInfoRequestDto signupInfoRequestDto) {
        this.phoneNumber = signupInfoRequestDto.getPhoneNumber();
        this.nickname = signupInfoRequestDto.getNickname();
        this.postNumber = signupInfoRequestDto.getPostNumber();
        this.age = signupInfoRequestDto.getAge();
        this.address = new Address(signupInfoRequestDto.getPostNumber(), signupInfoRequestDto.getStreetAddress(), signupInfoRequestDto.getDetailAddress());
        this.gender = Gender.valueOf(signupInfoRequestDto.getGender());
    }
}