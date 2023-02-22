package com.tophyuk.board.config;

import com.tophyuk.board.handler.CustomAuthFailureHandler;
import com.tophyuk.board.handler.CustomAuthSuccessHandler;
import com.tophyuk.board.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthSuccessHandler customAuthSuccessHandler;
    private final CustomAuthFailureHandler customAuthFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/css/**", "/img/**", "/signup", "/login", "/oauth-login", "/login/oauth2/code/google").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/board/**", "/main").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .usernameParameter("email")            // 아이디 파라미터명 설정
                        .passwordParameter("password")            // 패스워드 파라미터명 설정
                        .loginProcessingUrl("/login/action")
                        .defaultSuccessUrl("/main")
                        .successHandler(customAuthSuccessHandler)
                        .failureHandler(customAuthFailureHandler)
                        .permitAll()
                )
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .and()

                // OAuth 로그인
                .oauth2Login()  // OAuth2 설정 시작
                .loginPage("/login") // 로그인 페이지
                .userInfoEndpoint() // OAuth2 성공 히우 설정을 시작
                .userService(customOAuth2UserService); // customOAuthUserService 에서 처리

        return http.build();

    }


}
