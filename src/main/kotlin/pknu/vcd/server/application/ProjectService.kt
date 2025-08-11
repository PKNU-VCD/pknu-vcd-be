package pknu.vcd.server.application

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Caching
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pknu.vcd.server.application.dto.CreateProjectResponse
import pknu.vcd.server.application.dto.ProjectFileInfo
import pknu.vcd.server.application.dto.ProjectRequest
import pknu.vcd.server.application.exception.ProjectFileDisplayOrderNotValidException
import pknu.vcd.server.application.exception.ProjectNotFoundException
import pknu.vcd.server.domain.repository.ProjectRepository
import pknu.vcd.server.infra.cache.CacheNames

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val projectFileService: ProjectFileService,
) {

    @Transactional
    @Caching(
        evict = [
            CacheEvict(cacheNames = [CacheNames.PROJECT_PUBLIC_SUMMARIES], allEntries = true),
            CacheEvict(cacheNames = [CacheNames.PROJECT_ADMIN_SUMMARIES], allEntries = true)
        ]
    )
    fun createProject(request: ProjectRequest): CreateProjectResponse {
        validateDisplayOrder(request.fileUrls)

        val project = request.toProject()
        val savedProject = projectRepository.save(project)
        projectFileService.createProjectFiles(projectId = project.id, fileUrls = request.fileUrls)

        return CreateProjectResponse(projectId = savedProject.id)
    }

    @Transactional
    @Caching(
        evict = [
            CacheEvict(cacheNames = [CacheNames.PROJECT_PUBLIC_SUMMARIES], allEntries = true),
            CacheEvict(cacheNames = [CacheNames.PROJECT_ADMIN_SUMMARIES], allEntries = true),
            CacheEvict(cacheNames = [CacheNames.PROJECT_DETAILS], key = "#projectId")
        ]
    )
    fun updateProject(request: ProjectRequest, projectId: Long) {
        val project = projectRepository.findByIdOrNull(projectId)
            ?: throw ProjectNotFoundException(projectId)

        validateDisplayOrder(request.fileUrls)

        project.update(request.toUpdateCommand())

        projectFileService.deleteAllByProjectId(projectId)
        projectFileService.createProjectFiles(projectId, request.fileUrls)
    }

    private fun validateDisplayOrder(fileUrls: List<ProjectFileInfo>) {
        if (fileUrls.isEmpty()) return

        val displayOrders = fileUrls.map { it.displayOrder }.sorted()
        if (displayOrders.first() != 1 || displayOrders.last() != displayOrders.size) {
            throw ProjectFileDisplayOrderNotValidException()
        }

        val uniqueOrders = displayOrders.toSet()
        if (uniqueOrders.size != displayOrders.size) {
            throw ProjectFileDisplayOrderNotValidException()
        }
    }
}