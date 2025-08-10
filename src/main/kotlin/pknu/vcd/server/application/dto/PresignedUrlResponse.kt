package pknu.vcd.server.application.dto

data class PresignedUrlResponse(
    val presignedUrl: String,
    val fileUrl: String,
)
