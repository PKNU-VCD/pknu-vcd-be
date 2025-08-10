package pknu.vcd.server.api.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import pknu.vcd.server.application.dto.CreateGuestBookEntryRequest

data class CreateGuestBookEntryWebRequest(
    @field:NotNull
    @field:Size(max = 100)
    val content: String,
) {

    fun toAppRequest(clientIp: String): CreateGuestBookEntryRequest {
        return CreateGuestBookEntryRequest(
            content = content,
            clientIp = clientIp,
        )
    }
}