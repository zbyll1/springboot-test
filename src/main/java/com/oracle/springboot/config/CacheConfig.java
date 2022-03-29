package com.oracle.springboot.config;

import com.oracle.springboot.cache.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

/**
 * @Description:
 * @Author: Tu Xu
 * @CreateDate: 2001/1/1 2:18
 * @Version: 1.0
 **/
@Configuration
@EnableCaching
public class CacheConfig {
    @Autowired
    private RedisCache redisCache;

    @Bean("cacheManager")
    public SimpleCacheManager simpleCacheManager(){
        SimpleCacheManager simpleCacheManager=new SimpleCacheManager();
        HashSet<Cache> set=new HashSet<>();
        redisCache.setName("redisCache");
        set.add(redisCache);

        simpleCacheManager.setCaches(set);
        return simpleCacheManager;
    }
}
