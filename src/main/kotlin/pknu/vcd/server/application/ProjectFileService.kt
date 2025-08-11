package pknu.vcd.server.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pknu.vcd.server.application.dto.ProjectFileInfo
import pknu.vcd.server.domain.ProjectFile
import pknu.vcd.server.domain.repository.ProjectFileRepository

@Service
class ProjectFileService(
    private val projectFileRepository: ProjectFileRepository,
) {

    @Transactional(readOnly = true)
    fun getByProjectId(projectId: Long): List<ProjectFile> {
        return projectFileRepository.findAllByProjectIdOrderByDisplayOrderAsc(projectId)
    }

    @Transactional
    fun createProjectFiles(projectId: Long, fileUrls: List<ProjectFileInfo>) {
        val projectFiles = fileUrls.map {
            ProjectFile(
                projectId = projectId,
                fileUrl = it.url,
                displayOrder = it.displayOrder
            )
        }
        projectFileRepository.saveAll(projectFiles)
    }

    @Transactional
    fun deleteAllByProjectId(projectId: Long) {
        projectFileRepository.deleteByProjectId(projectId)
    }
}