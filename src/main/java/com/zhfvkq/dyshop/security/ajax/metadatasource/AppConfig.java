package com.zhfvkq.dyshop.security.ajax.metadatasource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public SecurityResourceService securityResourceService(ResourcesRepository resourcesRepository){
        return new SecurityResourceService(resourcesRepository);
    }

}
