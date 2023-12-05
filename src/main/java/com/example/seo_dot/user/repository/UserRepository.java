package com.example.seo_dot.user.repository;

import com.example.seo_dot.user.domain.OauthId;
import com.example.seo_dot.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByOauthId(OauthId oauthId);

    Optional<User> findByNickname(String nickname);
}
