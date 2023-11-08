package com.example.seo_dot.user.domain;

import com.example.seo_dot.user.domain.enums.Gender;
import com.example.seo_dot.user.domain.enums.Platform;
import com.example.seo_dot.user.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
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

    @Embedded
    private Address address;
    private Date birth;
    private int age;
    private String picture;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Platform platform;

    private LocalDateTime deleted;
    private boolean activated;
    public User(String email, String nickname, Platform platform) {
        this.email = email;
        this.nickname = nickname;
        this.role = Role.USER;
        this.platform = platform;
    }

    public User oauthUpdate(String name, String picture) {
        this.nickname = name;
        this.picture = picture;

        return this;
    }
}
