package pknu.vcd.server.application

import pknu.vcd.server.application.dto.PresignedUrlResponse

fun interface PresignedUrlProviderPort {

    operator fun invoke(
        fileName: String,
        fileSize: Long,
        contentType: String,
    ): PresignedUrlResponse
}