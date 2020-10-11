package com.umbrella.leancloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 该类用来配置feign的日志
 */
@Configuration
public class OpenFeignLogConfig {
    @Bean
    public Logger.Level openFeignLogLevel(){
        return Logger.Level.FULL;
    }
}
