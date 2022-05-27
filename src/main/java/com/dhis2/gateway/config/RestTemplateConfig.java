package com.dhis2.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig
{
    @Value("${dhis2.username}")
    private String dhis2User;

    @Value("${dhis2.password}")
    private String dhis2Password;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.basicAuthentication(dhis2User, dhis2Password).build();
    }
}