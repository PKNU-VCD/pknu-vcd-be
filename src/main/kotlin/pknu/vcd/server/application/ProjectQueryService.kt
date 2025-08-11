package pknu.vcd.server.application

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pknu.vcd.server.application.dto.ProjectDetailsResponse
import pknu.vcd.server.application.dto.ProjectPublicSummaryResponse
import pknu.vcd.server.application.exception.ProjectNotFoundException
import pknu.vcd.server.domain.Category
import pknu.vcd.server.domain.dto.ProjectAdminSummaryDto
import pknu.vcd.server.domain.repository.ProjectRepository
import pknu.vcd.server.infra.cache.CacheNames

@Service
class ProjectQueryService(
    private val projectRepository: ProjectRepository,
    private val projectFileService: ProjectFileService,
) {

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = [CacheNames.PROJECT_PUBLIC_SUMMARIES])
    fun getAllProjectPublicSummaries(): List<ProjectPublicSummaryResponse> {
        val summaries = projectRepository.findAllProjectPublicSummaries()

        return summaries.map {
            ProjectPublicSummaryResponse(
                projectId = it.projectId,
                designerNameKr = it.designerNameKr,
                projectNameKr = it.projectNameKr,
                thumbnailUrl = it.thumbnailUrl,
                categories = Category.fromCategoriesString(it.categoriesString)
            )
        }
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = [CacheNames.PROJECT_ADMIN_SUMMARIES])
    fun getAllProjectAdminSummaries(): List<ProjectAdminSummaryDto> {
        return projectRepository.findAllProjectAdminSummaries()
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = [CacheNames.PROJECT_DETAILS], key = "#projectId")
    fun getProjectDetails(projectId: Long): ProjectDetailsResponse {
        val project = projectRepository.findByIdOrNull(projectId)
            ?: throw ProjectNotFoundException(projectId)
        val projectFiles = projectFileService.getByProjectId(projectId)

        return ProjectDetailsResponse.of(project = project, projectFiles = projectFiles)
    }
}