package pknu.vcd.server.application.dto

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

data class ProjectFileInfo(
    val displayOrder: Int,
    val url: String,
)
