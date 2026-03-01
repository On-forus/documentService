package com.service.document.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class UtilConfig {


    @Value("${document-api.config.threads}")
    String threadSize;

    @Bean
    public Executor singleThread(){
        return Executors.newSingleThreadExecutor();
    }

    @Bean
    public Executor fixedThreadPool(){
        return Executors.newFixedThreadPool(Integer.parseInt(threadSize));
    }


}
