package pknu.vcd.server.application

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pknu.vcd.server.application.dto.BannedGuestBookResponse
import pknu.vcd.server.application.dto.CreateGuestBookRequest
import pknu.vcd.server.application.dto.CreateGuestBookResponse
import pknu.vcd.server.application.exception.GuestBookNotFoundException
import pknu.vcd.server.domain.GuestBook
import pknu.vcd.server.domain.dto.GuestBookSummaryDto
import pknu.vcd.server.domain.repository.GuestBookRepository
import pknu.vcd.server.infra.cache.CacheNames

@Service
class GuestBookService(
    private val guestBookRepository: GuestBookRepository,
) {

    @Transactional(readOnly = true)
    @Cacheable(value = [CacheNames.GUEST_BOOKS])
    fun getGuestBooks(): List<GuestBookSummaryDto> {
        return guestBookRepository.findAllByBannedFalse()
    }

    @Transactional
    @CacheEvict(value = [CacheNames.GUEST_BOOKS], allEntries = true)
    fun createGuestBook(request: CreateGuestBookRequest, clientIp: String): CreateGuestBookResponse {
        val guestBook = GuestBook(content = request.content, clientIp = clientIp)
        val savedGuestBook = guestBookRepository.save(guestBook)

        return CreateGuestBookResponse(
            guestBookId = savedGuestBook.id,
            content = savedGuestBook.content,
            createdAt = savedGuestBook.createdAt,
        )
    }

    @Transactional
    @CacheEvict(value = [CacheNames.GUEST_BOOKS], allEntries = true)
    fun banGuestBook(guestBookId: Long): BannedGuestBookResponse {
        val guestBook = guestBookRepository.findByIdOrNull(guestBookId)
            ?: throw GuestBookNotFoundException()

        guestBook.ban()

        return BannedGuestBookResponse(
            id = guestBook.id,
            content = guestBook.content,
            clientIp = guestBook.clientIp,
            banned = guestBook.banned,
            createdAt = guestBook.createdAt,
        )
    }
}