package pknu.vcd.server.api

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pknu.vcd.server.api.dto.CreateGuestBookEntryWebRequest
import pknu.vcd.server.application.GuestBookService
import pknu.vcd.server.application.dto.CreateGuestBookEntryResponse
import pknu.vcd.server.domain.dto.GuestBookEntryDto

@RestController
class GuestBookController(
    private val guestBookService: GuestBookService,
) {

    @PostMapping("/guestbooks")
    fun createGuestBook(
        @RequestBody request: CreateGuestBookEntryWebRequest,
        httpServletRequest: HttpServletRequest,
    ): CreateGuestBookEntryResponse {
        val clientIp = extractClientIp(httpServletRequest)
        val appRequest = request.toAppRequest(clientIp)

        return guestBookService.createGuestBookEntry(appRequest)
    }

    @GetMapping("/guestbooks")
    fun getGuestBooks(): List<GuestBookEntryDto> {
        return guestBookService.getGuestBookEntries();
    }

    fun extractClientIp(request: HttpServletRequest): String {
        val xfHeader = request.getHeader("X-Forwarded-For")
        if (xfHeader.isNullOrEmpty()) {
            return request.remoteAddr
        }
        return xfHeader.split(",").first().trim()
    }
}