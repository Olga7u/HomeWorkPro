package ru.otus.java.pro.homework1;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class HelloOtus {
    private final static int CACHETIME = 10;
    private final Cache<String, String> cache = CacheBuilder.newBuilder().softValues().expireAfterWrite(Duration.ofSeconds(CACHETIME)).build();

    private void printCache(){
        String value = cache.getIfPresent("KEY");
        System.out.println("get from cache: "+(value != null ? value : "cache is empty"));
    }

    void cacheTest(){
        System.out.println("put in cache: Hello World!");
        String key = "KEY";
        cache.put(key,"Hello World!");

        printCache();

        System.out.println("waiting "+CACHETIME+" sec...");
        try {
            TimeUnit.SECONDS.sleep(CACHETIME);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        printCache();
    }
}
