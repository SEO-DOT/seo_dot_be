package com.example.seo_dot.user.domain;

import com.example.seo_dot.user.domain.enums.Gender;
import com.example.seo_dot.user.domain.enums.Platform;
import com.example.seo_dot.user.domain.enums.UserRoleEnum;
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

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Embedded
    private Address address;

    private Gender gender;
    private Platform platform;
    private boolean activated;
    private boolean deleted;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public User(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}