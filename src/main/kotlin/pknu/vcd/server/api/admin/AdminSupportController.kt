package pknu.vcd.server.api.admin

import org.springframework.cache.CacheManager
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pknu.vcd.server.api.response.ApiResponse
import pknu.vcd.server.application.GuestBookService
import pknu.vcd.server.application.dto.BannedGuestBookResponse
import pknu.vcd.server.infra.cache.CacheNames

@RestController
class AdminSupportController(
    private val guestBookService: GuestBookService,
    private val cacheManager: CacheManager,
) {

    @PostMapping("/admin/guestbooks/{guestBookId}/ban")
    fun banGuestBook(
        @PathVariable guestBookId: Long,
    ): ResponseEntity<ApiResponse<BannedGuestBookResponse>> {
        val response = guestBookService.banGuestBook(guestBookId)
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @PostMapping("/admin/clear-caches")
    fun clearCaches(
        @RequestParam(defaultValue = "all") name: String,
    ): ResponseEntity<ApiResponse<*>> {
        val clearedCaches = when (name) {
            "all" -> clearAllCaches()
            "projects" -> clearProjectCaches()
            "guestbooks" -> clearGuestBookCaches()
            else -> emptyList()
        }

        val message = "캐시가 성공적으로 삭제되었습니다."
        val response = ClearedCacheResponse(
            message = message,
            clearedCaches = clearedCaches
        )

        return ResponseEntity.ok(ApiResponse.success(response))
    }

    data class ClearedCacheResponse(
        val message: String,
        val clearedCaches: List<String>,
    )

    private fun clearAllCaches(): List<String> {
        val cleared = mutableListOf<String>()
        cacheManager.cacheNames.forEach {
            cacheManager.getCache(it)?.clear()
            cleared.add(it)
        }
        return cleared
    }

    private fun clearProjectCaches(): List<String> {
        val targets = listOf(
            CacheNames.PROJECT_PUBLIC_SUMMARIES,
            CacheNames.PROJECT_ADMIN_SUMMARIES,
            CacheNames.PROJECT_DETAILS
        )
        targets.forEach {
            cacheManager.getCache(it)?.clear()
        }
        return targets
    }

    private fun clearGuestBookCaches(): List<String> {
        val targets = listOf(
            CacheNames.GUEST_BOOKS
        )
        targets.forEach {
            cacheManager.getCache(it)?.clear()
        }
        return targets
    }
}
