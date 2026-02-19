package com.service.document.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class UtilConfig {
    @Bean
    @Scope("prototype")
    public Executor getExecutor(){
        return Executors.newCachedThreadPool();
    }

}
