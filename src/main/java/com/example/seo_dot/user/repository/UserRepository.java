package com.example.seo_dot.user.repository;

import com.example.seo_dot.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
