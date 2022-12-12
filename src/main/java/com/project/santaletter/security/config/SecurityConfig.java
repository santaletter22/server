package com.project.santaletter.security.config;

import com.project.santaletter.security.filter.authenticationFailureHandler;
import com.project.santaletter.security.filter.authenticationSuccessHandler;
import com.project.santaletter.security.provider.CustomOAuth2UserService;
import com.project.santaletter.security.session.SecuritySessionExpiredStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig<S extends Session> {

    private final SecuritySessionExpiredStrategy securitySessionExpiredStrategy;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final authenticationSuccessHandler successHandler;
    private final authenticationFailureHandler failureHandler;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement(s -> { s
                    .maximumSessions(1) //동시 접속 1개
                    .maxSessionsPreventsLogin(false) //true: 새로 접속이 안됨, false: 기존 접속이 끊김
                    .expiredSessionStrategy(securitySessionExpiredStrategy);
                })

                .authorizeRequests(authorize -> {
                    try {
                        authorize
                                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .antMatchers("/user/**").access("hasRole('ROLE_USER')")
                                .antMatchers("/admin/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                                .anyRequest().permitAll()
                                .and()
                                .logout()
                                .logoutSuccessUrl("/")
                                .and()
                                .oauth2Login().loginPage("/login")
                                .successHandler(successHandler)
                                .failureHandler(failureHandler)
                                .userInfoEndpoint().userService(customOAuth2UserService);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .build();
    }

//    @Bean
//    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
//        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
//    }
}
