package pknu.vcd.server.infra.s3

import org.springframework.stereotype.Component
import pknu.vcd.server.application.PresignedUrlProviderPort
import pknu.vcd.server.application.dto.PresignedUrlResponse
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.net.URI
import java.time.Duration
import java.util.*

@Component
class S3PresignedUrlProviderAdapter(
    private val s3Presigner: S3Presigner,
    private val properties: AwsS3Properties,
) : PresignedUrlProviderPort {

    override fun invoke(fileName: String, fileSize: Long, contentType: String): PresignedUrlResponse {
        val uniqueFileName = generateUniqueFileName(fileName)

        val presignRequest = buildPresignedRequest(uniqueFileName, fileSize, contentType)

        val presignedUrl = s3Presigner.presignPutObject(presignRequest).url().toString()
        val fileUrl = createFileUrl(uniqueFileName)

        return PresignedUrlResponse(presignedUrl, fileUrl)
    }

    private fun generateUniqueFileName(fileName: String): String {
        val fileExtension = fileName.substringAfterLast('.', "")
        val uniqueId = UUID.randomUUID().toString().replace("-", "")

        return if (fileExtension.isNotEmpty()) {
            "$uniqueId.$fileExtension"
        } else {
            uniqueId
        }
    }

    private fun createFileUrl(fileName: String): String {
        val domain = URI.create(properties.endpoint)

        return domain.resolve(fileName).toString()
    }

    private fun buildPresignedRequest(fileName: String, fileSize: Long, contentType: String): PutObjectPresignRequest {
        val requestBuilder = PutObjectRequest.builder()
            .bucket(properties.bucket)
            .contentLength(fileSize)
            .contentType(contentType)
            .key(fileName)

        return PutObjectPresignRequest.builder()
            .signatureDuration(PRESIGNED_URL_EXPIRATION)
            .putObjectRequest(requestBuilder.build())
            .build()
    }

    companion object {
        private val PRESIGNED_URL_EXPIRATION = Duration.ofMinutes(10)
    }
}