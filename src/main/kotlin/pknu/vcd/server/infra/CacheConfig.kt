package pknu.vcd.server.infra

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit


@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager(): CaffeineCacheManager {
        val cacheManager = CaffeineCacheManager()
        cacheManager.setCaffeine(caffeineCacheBuilder())
        return cacheManager
    }

    private fun caffeineCacheBuilder(): Caffeine<Any, Any> {
        return Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(200)
            .expireAfterAccess(3, TimeUnit.DAYS)
    }
}
