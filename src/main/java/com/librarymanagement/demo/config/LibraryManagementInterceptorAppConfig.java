package com.librarymanagement.demo.config;

import com.librarymanagement.demo.interceptor.LibraryManagementInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class LibraryManagementInterceptorAppConfig implements WebMvcConfigurer {
    @Autowired
    LibraryManagementInterceptor libraryManagementInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(libraryManagementInterceptor)
                .addPathPatterns("/api/**"); // will allow all requests

    }
}
