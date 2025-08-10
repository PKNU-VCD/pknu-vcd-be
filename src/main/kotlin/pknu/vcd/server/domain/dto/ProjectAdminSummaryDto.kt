package pknu.vcd.server.domain.dto

import java.time.LocalDateTime

data class ProjectAdminSummaryDto(
    val projectId: Long,
    val designerNameKr: String,
    val projectNameKr: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
