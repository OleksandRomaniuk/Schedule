package com.schedule.proj.config;

import com.schedule.proj.service.UserService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class ProjectCachingConfig {

    @Bean
    public NaukmaCacheManager naukmaCacheManager() {
        return new NaukmaCacheManager();
    }
}
