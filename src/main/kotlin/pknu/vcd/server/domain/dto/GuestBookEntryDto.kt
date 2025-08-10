package pknu.vcd.server.domain.dto

import java.time.LocalDateTime

data class GuestBookEntryDto(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime,
)