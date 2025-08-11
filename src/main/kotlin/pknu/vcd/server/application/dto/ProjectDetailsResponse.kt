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

    companion object {

        fun of(project: Project, projectFiles: List<ProjectFile>): ProjectDetailsResponse {
            val categories = Category.fromCategoriesString(project.categoriesString)
            val files = projectFiles.map {
                ProjectFileInfo(displayOrder = it.displayOrder, url = it.fileUrl)
            }

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
                categories = categories,
                files = files
            )
        }
    }
}
