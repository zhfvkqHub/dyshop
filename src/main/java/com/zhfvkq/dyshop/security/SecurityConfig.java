package io.security.corespringsecurity.security.configs;

import com.zhfvkq.dyshop.security.ajax.AjaxAuthenticationProvider;
import com.zhfvkq.dyshop.security.ajax.AjaxLoginProcessingFilter;
import com.zhfvkq.dyshop.security.CustomAccessDeniedHandler;
import com.zhfvkq.dyshop.security.provider.CustomAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Order(1)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private String[] permitAllResources = {"/api/public/**","/sample","/aws/**","/sample/**", "/login/**", "/outer/**", "/fonts/**", "/landing/**", "/error/**", "/aws/health/check"};

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 정적 파일은 보안 필터 거치지 않음
        web.ignoring()
                .antMatchers(permitAllResources)
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ajaxAuthenticationProvider());
    }
    @Bean
    private AuthenticationProvider ajaxAuthenticationProvider() {
        return new AjaxAuthenticationProvider();
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")
                .authorizeRequests()
                .anyRequest().authenticated();
        http
                .addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class); // ajax 로그인

        http
                .csrf().disable(); // post 방식일 때는 필수
    }

    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
        AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();
        ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManager());
        return ajaxLoginProcessingFilter;
    }
    @Bean
    private AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
        ((CustomAccessDeniedHandler) accessDeniedHandler).setErrorPage("/denied");
        return accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
