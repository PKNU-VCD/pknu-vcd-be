package pknu.vcd.server.domain.dto

data class ProjectPublicSummaryDto(
    val projectId: Long,
    val designerNameKr: String,
    val projectNameKr: String,
    val thumbnailUrl: String,
    val categoriesString: String,
)
