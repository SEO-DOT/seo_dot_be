package com.example.seo_dot.user.repository;

import com.example.seo_dot.user.domain.User;
import com.querydsl.core.group.GroupBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickName(String nickName);
    Optional<User> findByEmail(String email);
}
