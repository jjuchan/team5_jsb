package com.team5_jsb.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final Environment environment;

    // 생성자를 통한 의존성 주입 - Environment 객체를 받아옴
    public SecurityConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity httpSecurity = http
            // CSRF(Cross-Site Request Forgery) 공격 방지 비활성화 (개발 편의상)
            .csrf(csrf -> csrf.disable())
            
            // HTTP 요청에 대한 접근 권한 설정
            .authorizeHttpRequests(auth -> {
                // 회원가입, 로그인 페이지는 누구나 접근 가능
                auth.requestMatchers("/user/signup", "/user/login").permitAll()
                    // 정적 리소스(CSS, JS, 이미지)는 누구나 접근 가능
                    .requestMatchers("/css/**", "/js/**", "/images/**").permitAll();
                
                // 개발 환경(dev 프로필)에서만 H2 Console 접근 허용
                if (environment.acceptsProfiles("dev")) {
                    auth.requestMatchers("/h2-console/**").permitAll();
                }
                
                // /admin으로 시작하는 URL은 ADMIN 권한을 가진 사용자만 접근 가능
                auth.requestMatchers("/admin/**").hasRole("ADMIN")
                    // 위에서 명시하지 않은 모든 요청은 인증 필요
                    .anyRequest().authenticated();
            })
            
            // 폼 기반 로그인 설정
            .formLogin(form -> form
                .loginPage("/user/login")           // 로그인 페이지 경로
                .defaultSuccessUrl("/")             // 로그인 성공 시 리다이렉트할 기본 페이지
                .failureUrl("/user/login?error")    // 로그인 실패 시 리다이렉트할 페이지
            )
            
            // 로그아웃 설정
            .logout(logout -> logout
                .logoutUrl("/user/logout")          // 로그아웃 요청 URL
                .logoutSuccessUrl("/user/login")    // 로그아웃 성공 시 리다이렉트할 페이지
            );
            
        // 개발 환경에서만 H2 Console의 iframe 사용 허용 (X-Frame-Options 설정)
        if (environment.acceptsProfiles("dev")) {
            httpSecurity.headers(headers -> headers.frameOptions().sameOrigin());
        }
        
        // 설정된 HttpSecurity 객체를 SecurityFilterChain으로 빌드하여 반환
        return httpSecurity.build();
    }
}
