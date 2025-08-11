package pknu.vcd.server.application.dto

import java.time.LocalDateTime

data class BannedGuestBookResponse(
    val id: Long,
    val content: String,
    val clientIp: String,
    val banned: Boolean,
    val createdAt: LocalDateTime,
)
