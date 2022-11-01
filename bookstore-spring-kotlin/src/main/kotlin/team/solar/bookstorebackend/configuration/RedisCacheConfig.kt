package team.solar.bookstorebackend.configuration

import org.springframework.cache.Cache
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RedisCacheConfig : CachingConfigurerSupport() {
    @Bean
    override fun errorHandler(): CacheErrorHandler? {
        return object : CacheErrorHandler {
            override fun handleCacheGetError(e: RuntimeException, cache: Cache, key: Any) {
                println("Cache Get Error $key")
            }

            override fun handleCachePutError(e: RuntimeException, cache: Cache, key: Any, value: Any?) {
                println("Cache Put Error $key")
            }

            override fun handleCacheEvictError(e: RuntimeException, cache: Cache, key: Any) {
                println("Cache Evict Error")
            }

            override fun handleCacheClearError(e: RuntimeException, cache: Cache) {
                println("Cache Clear Error")
            }
        }
    }
}
