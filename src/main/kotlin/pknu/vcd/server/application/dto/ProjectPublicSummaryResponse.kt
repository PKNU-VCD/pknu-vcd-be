package pknu.vcd.server.application.dto

import pknu.vcd.server.domain.Category

data class ProjectPublicSummaryResponse(
    val projectId: Long,
    val designerNameKr: String,
    val projectNameKr: String,
    val thumbnailUrl: String,
    val categories: List<Category>,
)
