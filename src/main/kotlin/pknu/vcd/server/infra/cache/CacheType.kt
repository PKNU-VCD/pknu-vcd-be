package pknu.vcd.server.infra.cache

import java.time.Duration

enum class CacheType(
    val cacheName: String,
    val initialCapacity: Int,
    val maximumSize: Long,
    val expireAfterWrite: Duration? = null,
    val expireAfterAccess: Duration? = null,
) {

    PROJECT_PUBLIC_SUMMARIES(
        cacheName = CacheNames.PROJECT_PUBLIC_SUMMARIES,
        initialCapacity = 1,
        maximumSize = 1,
        expireAfterAccess = Duration.ofDays(3)
    ),

    PROJECT_ADMIN_SUMMARIES(
        cacheName = CacheNames.PROJECT_ADMIN_SUMMARIES,
        initialCapacity = 1,
        maximumSize = 1,
        expireAfterAccess = Duration.ofDays(3)
    ),

    PROJECT_DETAILS(
        cacheName = CacheNames.PROJECT_DETAILS,
        initialCapacity = 50,
        maximumSize = 50,
        expireAfterAccess = Duration.ofDays(3)
    ),

    GUEST_BOOK_RATE_LIMITER(
        cacheName = CacheNames.GUEST_BOOK_RATE_LIMITER,
        initialCapacity = 50,
        maximumSize = 500,
        expireAfterWrite = Duration.ofSeconds(30)
    ),

    GUEST_BOOKS(
        cacheName = CacheNames.GUEST_BOOKS,
        initialCapacity = 1,
        maximumSize = 1,
        expireAfterAccess = Duration.ofDays(3)
    )
    ;
}

object CacheNames {

    const val PROJECT_PUBLIC_SUMMARIES = "project-public-summaries"
    const val PROJECT_ADMIN_SUMMARIES = "project-admin-summaries"
    const val PROJECT_DETAILS = "project-details"
    const val GUEST_BOOK_RATE_LIMITER = "guest-book-rate-limiter"
    const val GUEST_BOOKS = "guest-books"
}