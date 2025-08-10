package pknu.vcd.server.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pknu.vcd.server.application.dto.CreateGuestBookEntryRequest
import pknu.vcd.server.application.dto.CreateGuestBookEntryResponse
import pknu.vcd.server.domain.GuestBookEntry
import pknu.vcd.server.domain.dto.GuestBookEntryDto
import pknu.vcd.server.domain.GuestBookEntryRepository
import java.time.LocalDateTime

@Service
class GuestBookService(
    private val guestBookEntryRepository: GuestBookEntryRepository,
) {

    @Transactional
    fun createGuestBookEntry(request: CreateGuestBookEntryRequest): CreateGuestBookEntryResponse {
        checkRateLimit(request.clientIp)

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

    private fun checkRateLimit(clientIp: String) {
        val now = LocalDateTime.now()
        val recent = guestBookEntryRepository.findTopByClientIpAndCreatedAtAfterOrderByCreatedAtDesc(
            clientIp = clientIp,
            createdAt = now.minusMinutes(3)
        )

        if (recent != null) {
            throw IllegalStateException("You can only post once every 3 minutes. Please try again later.")
        }
    }
}