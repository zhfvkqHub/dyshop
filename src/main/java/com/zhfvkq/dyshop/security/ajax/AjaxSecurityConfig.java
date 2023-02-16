package com.zhfvkq.dyshop.security.ajax;

import com.zhfvkq.dyshop.security.CustomAccessDeniedHandler;
import com.zhfvkq.dyshop.security.ajax.metadatasource.PermitAllFilter;
import com.zhfvkq.dyshop.security.ajax.metadatasource.SecurityResourceService;
import com.zhfvkq.dyshop.security.ajax.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import com.zhfvkq.dyshop.security.ajax.metadatasource.UrlResourcesMapFactoryBean;
import com.zhfvkq.dyshop.security.provider.CustomAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
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
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Configuration
@EnableWebSecurity
@Order(0)
@Slf4j
public class AjaxSecurityConfig extends WebSecurityConfigurerAdapter {
    private String[] permitAllResources = {"/api/public/**","/sample","/aws/**","/sample/**", "/login/**", "/outer/**", "/fonts/**", "/landing/**", "/error/**", "/aws/health/check"};

    @Autowired
    private SecurityResourceService securityResourceService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 정적 파일은 보안 필터 거치지 않음
        web.ignoring()
                .antMatchers(permitAllResources)
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    private AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception { // username, password
        http
                .authorizeRequests()
                .anyRequest().authenticated();

        http
                .addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class);

        http
                .addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class); // ajax 로그인

        http
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
        ;

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
        ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler());
        ajaxLoginProcessingFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler());
        return ajaxLoginProcessingFilter;
    }

    @Bean
    public AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler(){
        return new AjaxAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler ajaxAuthenticationFailureHandler(){
        return new AjaxAuthenticationFailureHandler();
    }
    @Bean
    private AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
        ((CustomAccessDeniedHandler) accessDeniedHandler).setErrorPage("/denied");
        return accessDeniedHandler;
    }

    //    db 동적 빈

    /**
     * DB 인가 처리
     * @return
     * @throws Exception
     */
    @Bean
    public PermitAllFilter customFilterSecurityInterceptor() throws Exception {
        PermitAllFilter permitAllFilter = new PermitAllFilter(permitAllResources);
        permitAllFilter.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        permitAllFilter.setAccessDecisionManager(affimativeBased()); // 접근 결정 관리자 affimativeBased : ROLE 하나만 만족해도 인가됨
        permitAllFilter.setAuthenticationManager(authenticationManagerBean()); // 인가 전 인증 매니저
        return permitAllFilter;
    }

    private AccessDecisionManager affimativeBased() {
        return new AffirmativeBased(getAccessDecistionVoters());
    }

    private List<AccessDecisionVoter<?>> getAccessDecistionVoters() {
        // role 상위 계층 구현
        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
        accessDecisionVoters.add(roleVoter());
        return accessDecisionVoters;
    }

    @Bean
    private AccessDecisionVoter<? extends Object> roleVoter() {
        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
        return roleHierarchyVoter;
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        return new RoleHierarchyImpl();
    }

    @Bean
    private FilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() throws Exception {
        // db로 받은 Map 정보 전달
        return new UrlFilterInvocationSecurityMetadataSource(urlResourcesMapFactoryBean().getObject(), securityResourceService);
    }

    private UrlResourcesMapFactoryBean urlResourcesMapFactoryBean() {
        UrlResourcesMapFactoryBean urlResourcesMapFactoryBean = new UrlResourcesMapFactoryBean();
        urlResourcesMapFactoryBean.setSecurityResourceService(securityResourceService);
        return urlResourcesMapFactoryBean;
    }
}
