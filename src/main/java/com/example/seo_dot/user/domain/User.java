package com.example.seo_dot.user.domain;

import com.example.seo_dot.user.domain.enums.Role;
import com.example.seo_dot.user.domain.enums.UserType;
import com.nimbusds.openid.connect.sdk.claims.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    private String address;
    private Date birth;
    private Gender gender;
    private int age;
    private boolean activated;
    private Role role;
    private LocalDateTime deleted;
    private UserType userType;

}
