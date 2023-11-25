package com.spotifyinfo.configuration;

import com.spotifyinfo.interceptors.HttpInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@RequiredArgsConstructor
@Component
public class InterceptorConfiguration extends WebMvcConfigurationSupport {
    private final HttpInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }

}