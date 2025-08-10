package pknu.vcd.server.infra

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("aws.s3")
data class AwsS3Properties(
    val accessKey: String,
    val secretKey: String,
    val region: String,
)
