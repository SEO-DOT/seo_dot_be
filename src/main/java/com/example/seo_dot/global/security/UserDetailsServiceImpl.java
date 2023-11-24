package com.example.seo_dot.global.security;

import com.example.seo_dot.user.domain.OauthId;
import com.example.seo_dot.user.domain.User;
import com.example.seo_dot.user.domain.enums.Platform;
import com.example.seo_dot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(OauthId oauthId) throws UsernameNotFoundException{
        User user = userRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + oauthId.getOauthServerId()));

        log.info("user ={}", user.getEmail());
        return new UserDetailsImpl(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + email));

        return new UserDetailsImpl(user);
    }
}
