package pknu.vcd.server.application.dto

data class CreateGuestBookEntryRequest(
    val content: String,
    val clientIp: String,
)