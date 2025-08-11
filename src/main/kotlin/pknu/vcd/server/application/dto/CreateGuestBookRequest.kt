package pknu.vcd.server.application.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateGuestBookRequest(
    @field:NotBlank(message = "내용은 비어있을 수 없습니다.")
    @field:Size(max = 100, message = "내용은 최대 100자까지 입력할 수 있습니다.")
    val content: String,
)