package com.zhfvkq.dyshop.security.ajax.metadatasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class SecurityResourceService {

    private ResourceRepository resourceRepository;

    public SecurityResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    // db 자원 파싱
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList(){

        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resocuces> resourceList = resourceRepository.findAllResources();

        // ex) /member , ROLE_ADMIN, ROME_USER
        //     /main , ROLE_MEMBER
        resourceList.forEach(r -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            r.getRoleSet().forEach(role ->{
                configAttributeList.add(new SecurityConfig(role.getRoleName()));
                result.put(new AntPathRequestMatcher(re.getResourceName()), configAttributeList);
            });

        });

        return result;
    }
}
