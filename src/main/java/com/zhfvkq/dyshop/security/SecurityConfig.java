package com.zhfvkq.dyshop.security;

import com.zhfvkq.dyshop.member.service.CustomUserDetailsService;
import com.zhfvkq.dyshop.security.provider.CustomAuthenticationProvider;
import io.security.corespringsecurity.security.filter.AjaxLoginProcessingFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationSuccess authenticationSuccess;
    private final AuthenticationFailure authenticationFailure;
    private final LogoutExecute logoutExecute;
    private final LogoutSuccess logoutSuccess;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationEntryException authenticationEntryException;
    private final AccessDeniedHandlerException accessDeniedHandlerException;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationProvider authProvider() {
        return new CustomAuthenticationProvider();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
        ;

        http.formLogin() // 로그인
                .loginPage("/member/login")
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
                .deleteCookies("SESSION", "JSESSIONID", "remember-me")
                .addLogoutHandler(logoutExecute)
                .logoutSuccessHandler(logoutSuccess)
        ;

        http.rememberMe() // 사용자 저장
                .rememberMeParameter("idMaintain") // default 파라미터는 remember-me
                .tokenValiditySeconds(604800) // 7일로 설정(default 14일)
                .alwaysRemember(false)
                .userDetailsService(customUserDetailsService)
        ;

        http.sessionManagement()
                .maximumSessions(1) // -1 무제한
                .expiredUrl("/member/login") // 세션 만료
        ;

        http.exceptionHandling() // Exception 처리
                .authenticationEntryPoint(authenticationEntryException) // 인증 예외
                .accessDeniedHandler(accessDeniedHandlerException) // 인가 예외
        ;

        http.authenticationProvider(authProvider());

        http.addFilterAfter(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
        AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();
        ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        return ajaxLoginProcessingFilter;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManagerBean();
    }


    /**
     * 정적 자원 및 루트 페이지 ignore
     */
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .antMatchers("/**");
//    }


}
