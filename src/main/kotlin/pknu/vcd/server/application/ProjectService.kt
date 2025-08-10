package pknu.vcd.server.application

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pknu.vcd.server.application.dto.ProjectDetailsResponse
import pknu.vcd.server.application.dto.ProjectFileUrl
import pknu.vcd.server.application.dto.ProjectRequest
import pknu.vcd.server.domain.Category
import pknu.vcd.server.domain.Project
import pknu.vcd.server.domain.ProjectRepository
import pknu.vcd.server.domain.dto.ProjectSummaryDto

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val projectFileService: ProjectFileService,
) {

    @Transactional(readOnly = true)
    fun getAllProjectSummaries(): List<ProjectSummaryDto> {
        return projectRepository.findAllProjectSummaries()
    }

    @Transactional(readOnly = true)
    fun getProjectDetails(projectId: Long): ProjectDetailsResponse {
        val project = projectRepository.findByIdOrNull(projectId)
            ?: throw IllegalArgumentException("존재하지 않는 프로젝트입니다.")
        val projectFiles = projectFileService.getByProjectId(projectId)

        return ProjectDetailsResponse.of(project = project, projectFiles = projectFiles)
    }

    @Transactional
    fun createProject(request: ProjectRequest): Long {
        validateOrder(request.fileUrls)

        val project = Project(
            designerEmail = request.designerEmail,
            designerNameKr = request.designerName.kr,
            designerNameEn = request.designerName.en,
            projectNameKr = request.projectName.kr,
            projectNameEn = request.projectName.en,
            descriptionKr = request.description.kr,
            descriptionEn = request.description.en,
            thumbnailUrl = request.thumbnailUrl,
            categoriesString = Category.toCategoriesString(request.categories)
        )

        projectFileService.createProjectFiles(projectId = project.id, fileUrls = request.fileUrls)

        return projectRepository.save(project).id
    }

    @Transactional
    fun updateProject(request: ProjectRequest, projectId: Long) {
        val project = projectRepository.findByIdOrNull(projectId)
            ?: throw IllegalArgumentException("존재하지 않는 프로젝트입니다.")

        validateOrder(request.fileUrls)

        project.designerEmail = request.designerEmail
        project.designerNameKr = request.designerName.kr
        project.designerNameEn = request.designerName.en
        project.projectNameKr = request.projectName.kr
        project.projectNameEn = request.projectName.en
        project.descriptionKr = request.description.kr
        project.descriptionEn = request.description.en
        project.thumbnailUrl = request.thumbnailUrl
        project.categoriesString = Category.toCategoriesString(request.categories)

        projectFileService.deleteAllByProjectId(projectId)
        projectFileService.createProjectFiles(projectId, request.fileUrls)
    }

    private fun validateOrder(fileUrls: List<ProjectFileUrl>) {
        val orders = fileUrls.map { it.order }.sorted()
        if (orders.isEmpty()) return

        if (orders.first() != 1 || orders.last() != orders.size) {
            throw IllegalArgumentException("order 값은 1부터 시작하여 순차적으로 증가해야 합니다.")
        }
    }
}