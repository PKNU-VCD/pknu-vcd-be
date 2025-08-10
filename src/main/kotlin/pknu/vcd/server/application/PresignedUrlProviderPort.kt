package pknu.vcd.server.application

import pknu.vcd.server.application.dto.PresignedUrl

fun interface PresignedUrlProviderPort {

    operator fun invoke(): PresignedUrl
}