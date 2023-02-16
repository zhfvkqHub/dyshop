package com.zhfvkq.dyshop.security.ajax.metadatasource;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * db 동적 인가 체크
 */
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap = new LinkedHashMap<>();
    private SecurityResourceService securityResourceService;
    public UrlFilterInvocationSecurityMetadataSource(LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourcesMap
                                                    ,SecurityResourceService securityResourceService) {
        this.requestMap = resourcesMap;
        this.securityResourceService = securityResourceService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 요청 url 객체
        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        // test data
        // requestMap.put(new AntPathRequestMatcher("/admin"), Arrays.asList(new SecurityConfig("ROLE_ADMIN"),new SecurityConfig("ROLE_MAGAGER")));

        if(requestMap != null){
            // ex) key : /admin , value : ROLE_ADMIN, ROLE_MAGAGER
            for(Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()){
                RequestMatcher matcher = entry.getKey(); // /admin
                // db 정보와 사용자 요청 정보 정보 일치 여부
                if(matcher.matches(request)) // /admin == /admin
                    return entry.getValue(); // Arrays.asList(new SecurityConfig("ROLE_ADMIN"),new SecurityConfig("ROLE_MAGAGER")
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();

        for(Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()){
            allAttributes.addAll(entry.getValue());
        }

        return allAttributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    /**
     * url 자원 실시간 반영 (추후 권한 변경 화면 만들어지면 호출)
     */
    public void reload(){
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> reloadedMap = securityResourceService.getResourceList();
        Iterator<Map.Entry<RequestMatcher, List<ConfigAttribute>>> iterator = reloadedMap.entrySet().iterator();

        requestMap.clear();

        while (iterator.hasNext()){
            Map.Entry<RequestMatcher, List<ConfigAttribute>> entry = iterator.next();
            requestMap.put(entry.getKey(), entry.getValue());
        }
    }

}
