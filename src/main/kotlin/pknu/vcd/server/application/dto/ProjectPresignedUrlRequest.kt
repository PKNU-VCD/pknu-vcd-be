package pknu.vcd.server.application.dto

data class ProjectPresignedUrlRequest(
    val fileName: String,
    val fileSize: Long,
    val contentType: String,
)

data class ProjectPresignedUrlBatchRequest(
    val files: List<ProjectPresignedUrlRequest>,
)