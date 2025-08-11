package pknu.vcd.server.application.dto

import pknu.vcd.server.domain.Category
import pknu.vcd.server.domain.Project
import pknu.vcd.server.domain.dto.ProjectUpdateCommand

data class ProjectRequest(
    val designerEmail: String,
    val designerName: DesignerName,
    val projectName: ProjectName,
    val description: Description,
    val categories: List<Category>,
    val thumbnailUrl: String,
    val fileUrls: List<ProjectFileInfo>,
) {

    fun toProject(): Project {
        val categoriesString = Category.toCategoriesString(this.categories)

        return Project(
            designerEmail = designerEmail,
            designerNameKr = designerName.kr,
            designerNameEn = designerName.en,
            projectNameKr = projectName.kr,
            projectNameEn = projectName.en,
            descriptionKr = description.kr,
            descriptionEn = description.en,
            thumbnailUrl = thumbnailUrl,
            categoriesString = categoriesString
        )
    }

    fun toUpdateCommand(): ProjectUpdateCommand {
        val categoriesString = Category.toCategoriesString(this.categories)

        return ProjectUpdateCommand(
            designerEmail = designerEmail,
            designerNameKr = designerName.kr,
            designerNameEn = designerName.en,
            projectNameKr = projectName.kr,
            projectNameEn = projectName.en,
            descriptionKr = description.kr,
            descriptionEn = description.en,
            thumbnailUrl = thumbnailUrl,
            categoriesString = categoriesString
        )
    }
}

