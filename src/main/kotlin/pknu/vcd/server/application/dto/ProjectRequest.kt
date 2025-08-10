package pknu.vcd.server.application.dto

import pknu.vcd.server.domain.Category

data class ProjectRequest(
    val designerEmail: String,
    val designerName: DesignerName,
    val projectName: ProjectName,
    val description: Description,
    val categories: List<Category>,
    val thumbnailUrl: String,
    val fileUrls: List<ProjectFileUrl>,
)

data class DesignerName(
    val kr: String,
    val en: String,
)

data class ProjectName(
    val kr: String,
    val en: String?,
)

data class Description(
    val kr: String,
    val en: String?,
)

data class ProjectFileUrl(
    val order: Int,
    val url: String,
)