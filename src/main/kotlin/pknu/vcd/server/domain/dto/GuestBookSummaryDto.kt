package pknu.vcd.server.domain.dto

import java.time.LocalDateTime

data class GuestBookSummaryDto(
    val guestBookId: Long,
    val content: String,
    val createdAt: LocalDateTime,
)