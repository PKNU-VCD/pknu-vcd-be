package pknu.vcd.server.infra.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager {
        val cacheManager = SimpleCacheManager()
        val caches = CacheType.entries.map { cacheType ->
            CaffeineCache(
                cacheType.cacheName,
                Caffeine.newBuilder()
                    .initialCapacity(cacheType.initialCapacity)
                    .maximumSize(cacheType.maximumSize)
                    .apply {
                        cacheType.expireAfterWrite?.let { expireAfterWrite(it) }
                        cacheType.expireAfterAccess?.let { expireAfterAccess(it) }
                    }
                    .build()
            )
        }
        cacheManager.setCaches(caches)
        return cacheManager
    }
}
