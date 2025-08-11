package pknu.vcd.server.api.guest

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pknu.vcd.server.api.response.ApiResponse
import pknu.vcd.server.application.GuestBookRateLimiter
import pknu.vcd.server.application.GuestBookService
import pknu.vcd.server.application.dto.CreateGuestBookRequest
import pknu.vcd.server.application.dto.CreateGuestBookResponse
import pknu.vcd.server.domain.dto.GuestBookSummaryDto

@RestController
class GuestBookController(
    private val guestBookService: GuestBookService,
    private val guestBookRateLimiter: GuestBookRateLimiter,
) {

    @GetMapping("/guestbooks")
    fun getGuestBooks(): ResponseEntity<ApiResponse<List<GuestBookSummaryDto>>> {
        val response = guestBookService.getGuestBooks()

        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @PostMapping("/guestbooks")
    fun createGuestBook(
        @RequestBody @Valid request: CreateGuestBookRequest,
        httpServletRequest: HttpServletRequest,
    ): ResponseEntity<ApiResponse<CreateGuestBookResponse>> {
        val clientIp = extractClientIp(httpServletRequest)
        guestBookRateLimiter.check(clientIp)

        val response = guestBookService.createGuestBook(request = request, clientIp = clientIp)

        return ResponseEntity.ok(ApiResponse.success(response))
    }

    private fun extractClientIp(request: HttpServletRequest): String {
        val xfHeader = request.getHeader("X-Forwarded-For")
        if (xfHeader.isNullOrBlank()) {
            return request.remoteAddr
        }
        return xfHeader.split(",").first().trim()
    }
}