package com.dhis2.test.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Data;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Data
@Configuration
@EnableCaching
public class CacheConfig {
    public static final String DATA_ELEMENTS_CACHE = "elementsCacheDatabase";

    /**
     * <p>This is a simple cache configured to a maximum size of 500
     * and a TTL of 30 minutes after first access.
     * The methods using this cache will filter by key, which in this case
     * it's the method name.
     * </p>
     * @return the built caffeine cache object
     */
    @Bean
    public CaffeineCache databaseCache() {
        return new CaffeineCache(DATA_ELEMENTS_CACHE, Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .build());
    }
}
