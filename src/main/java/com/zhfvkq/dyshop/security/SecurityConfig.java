package com.zhfvkq.dyshop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationSuccess authenticationSuccess;
    private final AuthenticationFailure authenticationFailure;
    private final LogoutExecute logoutExecute;
    private final LogoutSuccess logoutSuccess;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
        ;

        http.formLogin() // 로그인
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .failureUrl("/member/login?error=true")
                .usernameParameter("userId")
                .passwordParameter("password")
                .loginProcessingUrl("/member/login")
                .successHandler(authenticationSuccess)
                .failureHandler(authenticationFailure)
                .permitAll()
        ;

        http.logout() // 로그아웃
                .logoutUrl("/member/logout") // default post
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID")
                .addLogoutHandler(logoutExecute)
                .logoutSuccessHandler(logoutSuccess)
        ;

        http.rememberMe() // 사용자 저장
                .rememberMeParameter("idMaintain") // default 파라미터는 remember-me
                .tokenValiditySeconds(604800) // 7일로 설정(default 14일)
                .alwaysRemember(false)
                .userDetailsService(userDetailsService)
        ;

        http.sessionManagement()
                .maximumSessions(1) // -1 무제한
                .maxSessionsPreventsLogin(false) // true:로그인 제한 false(default):기존 세션 만료
                .expiredUrl("/member/login") // 세션 만료
        ;

        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/js/**","/css/**","/img/**", "/lib/**", "/");
    }


}
