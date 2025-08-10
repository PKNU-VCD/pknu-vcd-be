package pknu.vcd.server.infra

import org.springframework.stereotype.Component
import pknu.vcd.server.application.PresignedUrlProviderPort
import pknu.vcd.server.application.dto.PresignedUrl
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Component
class S3PresignedUrlProviderAdapter(
    private val properties: AwsS3Properties,
    private val s3Client: S3Client,
) : PresignedUrlProviderPort {

    override fun invoke(): PresignedUrl {
        TODO("Not yet implemented")
    }

    private fun s3Presigner(): S3Presigner {
        TODO()
//        return S3Presigner.builder()
//            .credentialsProvider(s3Client.credentialsProvider())
//            .region(s3Client.region())
//            .build()
    }
}