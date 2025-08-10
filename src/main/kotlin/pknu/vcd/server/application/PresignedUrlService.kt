package pknu.vcd.server.application

import org.springframework.stereotype.Service
import pknu.vcd.server.application.dto.PresignedUrlResponse
import pknu.vcd.server.application.dto.ProjectPresignedUrlBatchRequest
import pknu.vcd.server.application.dto.ProjectPresignedUrlRequest

@Service
class PresignedUrlService(
    private val presignedUrlProviderPort: PresignedUrlProviderPort,
) {

    fun createPresignedUrl(request: ProjectPresignedUrlRequest): PresignedUrlResponse {
        return presignedUrlProviderPort(
            fileName = request.fileName,
            fileSize = request.fileSize,
            contentType = request.contentType,
        )
    }

    fun createPresignedUrls(request: ProjectPresignedUrlBatchRequest): List<PresignedUrlResponse> {
        return request.files.map {
            presignedUrlProviderPort(
                fileName = it.fileName,
                fileSize = it.fileSize,
                contentType = it.contentType,
            )
        }
    }
}