package pknu.vcd.server.domain.dto

import java.time.LocalDateTime

data class ProjectSummaryDto(
    val id: Long,
    val designerName: String,
    val projectName: String,
    val createdAt: LocalDateTime,
)
