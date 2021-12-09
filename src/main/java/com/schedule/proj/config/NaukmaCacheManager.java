//package com.schedule.proj.config;
//
//import org.springframework.cache.Cache;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.concurrent.ConcurrentMapCache;
//import org.springframework.lang.Nullable;
//
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
//public class NaukmaCacheManager implements CacheManager {
//    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap(16);
//    private  Set<String> cacheNames = Collections.emptySet();
//    private final Collection<? extends Cache> caches = Arrays.asList(
//            new ConcurrentMapCache("subj"),
//            new ConcurrentMapCache("user")
//    );
//
//    public NaukmaCacheManager() {
//        initializeCaches();
//    }
//    protected Collection<? extends Cache> loadCaches() {
//        return this.caches;
//    }
//
//    public void initializeCaches() {
//        Collection<? extends Cache> caches = loadCaches();
//        synchronized(this.cacheMap) {
//            this.cacheNames = Collections.emptySet();
//            this.cacheMap.clear();
//            Set<String> cacheNames = new LinkedHashSet(caches.size());
//            Iterator var4 = caches.iterator();
//
//            while(var4.hasNext()) {
//                Cache cache = (Cache)var4.next();
//                String name = cache.getName();
//                this.cacheMap.put(name, cache);
//                cacheNames.add(name);
//            }
//
//            this.cacheNames = Collections.unmodifiableSet(cacheNames);
//        }
//    }
//
//    @Override
//    public Cache getCache(String name) {
//        return this.cacheMap.get(name);
//    }
//
//    @Override
//    public Collection<String> getCacheNames() {
//        return this.cacheNames;
//    }
//
//    public void evictAllCacheValues(String cacheName) {
//        getCache(cacheName).clear();
//    }
//
//}
//
