package pknu.vcd.server.application

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
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
    @Cacheable(cacheNames = ["project-summaries"])
    fun getAllProjectSummaries(): List<ProjectSummaryDto> {
        return projectRepository.findAllProjectSummaries()
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = ["product-details"], key = "#projectId")
    fun getProjectDetails(projectId: Long): ProjectDetailsResponse {
        val project = projectRepository.findByIdOrNull(projectId)
            ?: throw IllegalArgumentException("존재하지 않는 프로젝트입니다.")
        val projectFiles = projectFileService.getByProjectId(projectId)

        return ProjectDetailsResponse.of(project = project, projectFiles = projectFiles)
    }

    @Transactional
    @CacheEvict(cacheNames = ["project-summaries"])
    fun createProject(request: ProjectRequest): Long {
        validateDisplayOrder(request.fileUrls)

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
        val savedProject = projectRepository.save(project)
        projectFileService.createProjectFiles(projectId = project.id, fileUrls = request.fileUrls)

        return savedProject.id
    }

    @Transactional
    @Caching(
        evict = [
            CacheEvict(cacheNames = ["project-summaries"], allEntries = true),
            CacheEvict(cacheNames = ["product-details"], key = "#projectId")
        ]
    )
    fun updateProject(request: ProjectRequest, projectId: Long) {
        val project = projectRepository.findByIdOrNull(projectId)
            ?: throw IllegalArgumentException("존재하지 않는 프로젝트입니다.")

        validateDisplayOrder(request.fileUrls)

        project.apply {
            designerEmail = request.designerEmail
            designerNameKr = request.designerName.kr
            designerNameEn = request.designerName.en
            projectNameKr = request.projectName.kr
            projectNameEn = request.projectName.en
            descriptionKr = request.description.kr
            descriptionEn = request.description.en
            thumbnailUrl = request.thumbnailUrl
            categoriesString = Category.toCategoriesString(request.categories)
        }

        projectFileService.deleteAllByProjectId(projectId)
        projectFileService.createProjectFiles(projectId, request.fileUrls)
    }

    private fun validateDisplayOrder(fileUrls: List<ProjectFileUrl>) {
        val displayOrders = fileUrls.map { it.displayOrder }.sorted()
        if (displayOrders.isEmpty()) return

        if (displayOrders.first() != 1 || displayOrders.last() != displayOrders.size) {
            throw IllegalArgumentException("displayOrder 값은 1부터 시작하여 순차적으로 증가해야 합니다.")
        }
    }
}