package com.solution.githubrepolist.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Component
public class CacheHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheHelper.class);

    @Autowired
    private CacheManager cacheManager;

    public <T> Flux<T> getFromCacheOrFetch(String cacheName, String key, Class<T> type, Supplier<Flux<T>> fetchFunction) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            LOGGER.warn("Cache: {} not found. Fetching from source without caching.", cacheName);
            return fetchFunction.get();
        }

        T cachedValue = cache.get(key, type);
        if (cachedValue != null) {
            LOGGER.info("Found cached value for key: {} in cache: {}", key, cachedValue);
            return Flux.just(cachedValue);
        }

        LOGGER.info("No value found for key: {} in cache: {}. Fetching from source", key, cacheName);
        return fetchFunction.get().doOnNext(value -> {
            cache.put(key, value);
            LOGGER.info("Value for key: {} cached in cache: {}", key, cacheName);
        });
    }

    public String createKeyFromGivenArgs(String...args) {
        return String.join("-", args);
    }

}
