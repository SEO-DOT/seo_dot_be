package com.example.seo_dot.global.config;


import com.example.seo_dot.global.config.filter.JwtAuthorizationFilter;
import com.example.seo_dot.global.handler.OAuth2SuccessHandler;
import com.example.seo_dot.global.jwt.JwtProvider;
import com.example.seo_dot.global.security.UserDetailsServiceImpl;
import com.example.seo_dot.user.domain.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2SuccessHandler successHandler;

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(custom -> custom.disable())
                .sessionManagement(custom -> custom.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(custom -> custom.disable())
                .formLogin(custom -> custom.disable())
                .cors(corsConfigurer -> corsConfigurer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.addAllowedOriginPattern("*");
                        config.addAllowedMethod("*");
                        config.addAllowedHeader("*");
                        config.setAllowCredentials(true);

                        return config;
                    }
                }));

        http.authorizeHttpRequests(authorizeConfigurer ->
                authorizeConfigurer.anyRequest().authenticated()
        );

        http.oauth2Login(oauth2Configurer ->
                oauth2Configurer
                        .loginPage("/token/expired")
                        .successHandler(successHandler)
        );

        http.addFilterAfter(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtProvider, userDetailsService);
    }

}
