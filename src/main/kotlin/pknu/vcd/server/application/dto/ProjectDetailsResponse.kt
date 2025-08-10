package pknu.vcd.server.application.dto

import pknu.vcd.server.domain.Category
import pknu.vcd.server.domain.Project
import pknu.vcd.server.domain.ProjectFile

data class ProjectDetailsResponse(
    val projectId: Long,
    val designerEmail: String,
    val designerName: DesignerName,
    val projectName: ProjectName,
    val description: Description,
    val thumbnailUrl: String,
    val categories: List<Category>,
    val files: List<ProjectFileInfo>,
) {

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

    companion object {

        fun of(project: Project, projectFiles: List<ProjectFile>): ProjectDetailsResponse {
            return ProjectDetailsResponse(
                projectId = project.id,
                designerEmail = project.designerEmail,
                designerName = DesignerName(
                    kr = project.designerNameKr,
                    en = project.designerNameEn
                ),
                projectName = ProjectName(
                    kr = project.projectNameKr,
                    en = project.projectNameEn
                ),
                description = Description(
                    kr = project.descriptionKr,
                    en = project.descriptionEn
                ),
                thumbnailUrl = project.thumbnailUrl,
                categories = Category.fromCategoriesString(project.categoriesString),
                files = projectFiles.map { projectFile ->
                    ProjectFileInfo(displayOrder = projectFile.displayOrder, url = projectFile.fileUrl)
                }
            )
        }
    }
}
