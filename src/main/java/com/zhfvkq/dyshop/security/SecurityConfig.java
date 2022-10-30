package com.zhfvkq.dyshop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationSuccess authenticationSuccess;
    private final AuthenticationFailure authenticationFailure;
    private final LogoutExecute logoutExecute;
    private final LogoutSuccess logoutSuccess;
    private final UserDetailsService userDetailsService;
    private final AuthenticationEntryException authenticationEntryException;
    private final AccessDeniedHandlerException accessDeniedHandlerException;


//    @Bean
//    public UserDetailsManager member(DataSource dataSource) {
//        JdbcUserDetailsManager member = null;
//        UserDetails user = member.builder()
//                .username("userId")
//                .password("password")
//                .roles("role")
//                .build();
//        member = new JdbcUserDetailsManager(dataSource);
//        member.createUser(user);
//        return member;
//    }

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

        http.sessionManagement()
                .sessionFixation().changeSessionId() // default 세션 공격 보호
        ;

        http.exceptionHandling() // Exception 처리
                .authenticationEntryPoint(authenticationEntryException) // 인증 예외
                .accessDeniedHandler(accessDeniedHandlerException) // 인가 예외
        ;

//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 X (JWT 토큰 사용할거임)
//        ;

//        http.antMatcher("/shop/**") // 생략 시 전체경로 "/**"
//                .authorizeRequests()
//                .antMatchers("/shop/login/**").permitAll()
//                .antMatchers("/shop/mypage").hasRole("USER")
//                .antMatchers("/shop/admin/pay").access("hasROLE('REMP')")
//                .antMatchers("/shop/adminPay/**").access("hasRole('ADMIN') or hasRole('SYS')")
//                .anyRequest().authenticated()
//        ;

//        // 설정 시 구체적인 경로가 먼저 오고 그것 보다 큰 범위의 경로가 뒤에 오도록 설정 해야 함.

        return http.build();

    }


    /**
     * 정적 자원 및 루트 페이지 ignore
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .antMatchers("/", "/img/**", "/lib/**", "/member/**");
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
