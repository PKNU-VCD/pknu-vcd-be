package pknu.vcd.server.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pknu.vcd.server.application.dto.CreateGuestBookEntryRequest
import pknu.vcd.server.application.dto.CreateGuestBookEntryResponse
import pknu.vcd.server.domain.GuestBookEntry
import pknu.vcd.server.domain.GuestBookEntryDto
import pknu.vcd.server.domain.GuestBookEntryRepository

@Service
class GuestBookService(
    private val guestBookEntryRepository: GuestBookEntryRepository,
) {

    @Transactional
    fun createGuestBookEntry(request: CreateGuestBookEntryRequest): CreateGuestBookEntryResponse {
        val guestBookEntry = GuestBookEntry(content = request.content, clientIp = request.clientIp)
        val savedGuestBookEntry = guestBookEntryRepository.save(guestBookEntry)

        return CreateGuestBookEntryResponse(
            id = savedGuestBookEntry.id,
            content = savedGuestBookEntry.content,
            createdAt = savedGuestBookEntry.createdAt,
        )
    }

    @Transactional(readOnly = true)
    fun getGuestBookEntries(): List<GuestBookEntryDto> {
        return guestBookEntryRepository.findAllByBannedFalse()
    }
}