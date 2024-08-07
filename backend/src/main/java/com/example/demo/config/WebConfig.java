package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.FileCleanupInterceptor;


//Adding the interceptor in the requests in the spring boot
@Configuration
public class WebConfig implements WebMvcConfigurer{
    @Autowired
    private FileCleanupInterceptor fileCleanupInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(fileCleanupInterceptor);
    }
}
