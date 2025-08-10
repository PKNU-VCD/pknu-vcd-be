package pknu.vcd.server.application.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateGuestBookRequest(
    @field:NotNull
    @field:Size(max = 100)
    val content: String,
)