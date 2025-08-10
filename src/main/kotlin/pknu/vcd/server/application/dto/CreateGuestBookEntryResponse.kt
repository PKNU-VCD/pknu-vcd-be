package pknu.vcd.server.application.dto

import java.time.LocalDateTime

data class CreateGuestBookEntryResponse(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime,
)
