package pknu.vcd.server.domain.dto

data class ProjectUpdateCommand(
    val designerEmail: String,
    val designerNameKr: String,
    val designerNameEn: String,
    val projectNameKr: String,
    val projectNameEn: String?,
    val descriptionKr: String,
    val descriptionEn: String?,
    val thumbnailUrl: String,
    val categoriesString: String,
)