package com.leyoho.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot main Application
 *
 */
@SpringBootApplication
@EnableCaching
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CacheManager cacheManager() {
		GuavaCacheManager cacheManager = new GuavaCacheManager("fishes");
//		ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("greetings");
		return cacheManager;
	}
	
}
