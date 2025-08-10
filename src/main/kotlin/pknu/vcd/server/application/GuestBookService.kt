package pknu.vcd.server.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pknu.vcd.server.application.dto.CreateGuestBookRequest
import pknu.vcd.server.application.dto.CreateGuestBookResponse
import pknu.vcd.server.domain.GuestBook
import pknu.vcd.server.domain.repository.GuestBookRepository
import pknu.vcd.server.domain.dto.GuestBookSummaryDto

@Service
class GuestBookService(
    private val guestBookRepository: GuestBookRepository,
) {

    @Transactional(readOnly = true)
    fun getGuestBooks(): List<GuestBookSummaryDto> {
        return guestBookRepository.findAllByBannedFalse()
    }

    @Transactional
    fun createGuestBook(request: CreateGuestBookRequest, clientIp: String): CreateGuestBookResponse {
        val guestBook = GuestBook(content = request.content, clientIp = clientIp)
        val savedGuestBook = guestBookRepository.save(guestBook)

        return CreateGuestBookResponse(
            guestBookId = savedGuestBook.id,
            content = savedGuestBook.content,
            createdAt = savedGuestBook.createdAt,
        )
    }
}