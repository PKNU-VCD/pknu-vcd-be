package pknu.vcd.server.application.dto

import pknu.vcd.server.domain.Category

data class RegisterProjectRequest(
    val designer: Designer,
    val designerName: DesignerName,
    val projectName: ProjectName,
    val description: Description,
    val category: Category,
) {

    data class Designer(
        val name: String,
        val email: String,
    )

    data class DesignerName(
        val kr: String,
        val en: String?,
    )

    data class ProjectName(
        val kr: String,
        val en: String?,
    )

    data class Description(
        val kr: String,
        val en: String?,
    )
}
