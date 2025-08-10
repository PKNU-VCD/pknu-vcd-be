package pknu.vcd.server.api

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
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
    fun getGuestBooks(): List<GuestBookSummaryDto> {
        return guestBookService.getGuestBooks()
    }

    @PostMapping("/guestbooks")
    fun createGuestBook(
        @RequestBody request: CreateGuestBookRequest,
        httpServletRequest: HttpServletRequest,
    ): CreateGuestBookResponse {
        val clientIp = extractClientIp(httpServletRequest)
        guestBookRateLimiter.check(clientIp)

        return guestBookService.createGuestBook(request, clientIp)
    }

    private fun extractClientIp(request: HttpServletRequest): String {
        val xfHeader = request.getHeader("X-Forwarded-For")
        if (xfHeader.isNullOrEmpty()) {
            return request.remoteAddr
        }
        return xfHeader.split(",").first().trim()
    }
}