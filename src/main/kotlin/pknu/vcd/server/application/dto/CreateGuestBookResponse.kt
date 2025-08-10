package pknu.vcd.server.application.dto

import java.time.LocalDateTime

data class CreateGuestBookResponse(
    val guestBookId: Long,
    val content: String,
    val createdAt: LocalDateTime,
)
