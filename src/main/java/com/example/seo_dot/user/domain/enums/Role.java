package com.example.seo_dot.user.domain.enums;

import lombok.Getter;

@Getter
public enum Role {
    USER(Authority.USER),
    ADMIN(Authority.ADMIN);

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
