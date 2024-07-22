package com.solution.githubrepolist.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheStatsRunner implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheStatsRunner.class);

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void run(String... args) throws Exception {
        // Logowanie statystyk cache'u po uruchomieniu aplikacji
//        cacheStatsLogger.logCacheStats();

        Cache cache = cacheManager.getCache("nonForkRepositories");
        if (cache != null) {
            LOGGER.info("Cache 'nonForkRepositories' is available");
            cache.put("testKey", "testValue");
            String value = cache.get("testKey", String.class);
            LOGGER.info("Cached value for 'testKey': {}", value);
        } else {
            LOGGER.error("Cache 'nonForkRepositories' is not available");
        }
    }
}
