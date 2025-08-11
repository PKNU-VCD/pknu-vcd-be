package pknu.vcd.server.application

import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service
import pknu.vcd.server.application.exception.GuestBookCacheNotFoundException
import pknu.vcd.server.application.exception.GuestBookRateLimitExceededException
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
            ?: throw GuestBookCacheNotFoundException()

        val lastTime = cache.get(clientIp, LocalDateTime::class.java)
        if (lastTime != null) {
            val remainingSeconds = calculateRemainingSeconds(lastTime, now)
            throw GuestBookRateLimitExceededException(remainingSeconds)
        }

        cache.put(clientIp, now)
    }

    private fun calculateRemainingSeconds(lastTime: LocalDateTime, now: LocalDateTime): Long {
        val elapsed = Duration.between(lastTime, now).seconds
        return (60 - elapsed).coerceAtLeast(0)
    }
}