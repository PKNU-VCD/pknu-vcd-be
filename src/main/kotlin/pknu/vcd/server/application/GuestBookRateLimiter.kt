package pknu.vcd.server.application

import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service
import pknu.vcd.server.infra.cache.CacheNames
import java.time.Duration
import java.time.LocalDateTime

@Service
class GuestBookRateLimiter(
    private val cacheManager: CacheManager,
) {

    fun check(clientIp: String) {
        val now = LocalDateTime.now()
        val cache = cacheManager.getCache(CacheNames.GUEST_BOOK_RATE_LIMITER)
            ?: throw IllegalStateException("Rate limit cache not found")

        val lastTime = cache.get(clientIp, LocalDateTime::class.java)
        if (lastTime != null) {
            val elapsed = Duration.between(lastTime, now).seconds
            val remaining = (60 - elapsed).coerceAtLeast(0)
            throw IllegalStateException("60초에 한 번만 작성할 수 있습니다. 남은 시간: ${remaining}초")
        }

        cache.put(clientIp, now)
    }
}