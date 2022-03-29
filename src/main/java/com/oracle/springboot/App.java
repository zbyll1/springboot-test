package com.oracle.springboot;

import com.oracle.springboot.config.CacheConfig;
import com.oracle.springboot.config.WebMvcConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description:
 * @Author: Tu Xu
 * @CreateDate: 2022/3/24 11:35
 * @Version: 1.0
 **/
@SpringBootApplication
@MapperScan("com.oracle.springboot.mapper")
@EnableTransactionManagement
@EnableCaching
@Import({CacheConfig.class, WebMvcConfig.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}
