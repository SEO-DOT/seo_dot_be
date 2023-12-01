package com.example.seo_dot.user.service;

import com.example.seo_dot.global.jwt.JwtUtil;
import com.example.seo_dot.global.security.UserDetailsImpl;
import com.example.seo_dot.user.domain.OauthId;
import com.example.seo_dot.user.domain.User;
import com.example.seo_dot.user.domain.dto.SignupRequestDto;
import com.example.seo_dot.user.domain.enums.UserRoleEnum;
import com.example.seo_dot.user.model.SignupInfoRequestDto;
import com.example.seo_dot.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "UserService")
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum userRoleEnum = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            userRoleEnum = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, email, userRoleEnum);
        userRepository.save(user);
    }

    public void cookie(HttpServletResponse response) {

    }

    public String tokenRefresh(HttpServletRequest request) {
        String refreshToken = jwtUtil.getJwtFromHeader(request);
        jwtUtil.refreshTokenCheck(refreshToken);

        OauthId oauthId = jwtUtil.getOauthIdFromRedis(refreshToken);

        User user = userRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new IllegalArgumentException("no have user"));

        return jwtUtil.createAccessToken(user);
    }

    public void createsignupInfo(UserDetailsImpl userDetails, SignupInfoRequestDto signupInfoRequestDto) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow();

    }
}
