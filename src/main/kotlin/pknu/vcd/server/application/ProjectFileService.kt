package pknu.vcd.server.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pknu.vcd.server.application.dto.PresignedUrlResponse
import pknu.vcd.server.application.dto.ProjectFileUrl
import pknu.vcd.server.application.dto.ProjectPresignedUrlBatchRequest
import pknu.vcd.server.application.dto.ProjectPresignedUrlRequest
import pknu.vcd.server.domain.ProjectFile
import pknu.vcd.server.domain.ProjectFileRepository

@Service
class ProjectFileService(
    private val projectFileRepository: ProjectFileRepository,
    private val presignedUrlProviderPort: PresignedUrlProviderPort,
) {

    @Transactional
    fun createProjectFiles(projectId: Long, fileUrls: List<ProjectFileUrl>) {
        val projectFiles = fileUrls.map { ProjectFile(projectId = projectId, fileUrl = it.url, order = it.order) }
        projectFileRepository.saveAll(projectFiles)
    }

    @Transactional
    fun deleteAllByProjectId(projectId: Long) {
        projectFileRepository.deleteByProjectId(projectId)
    }

    @Transactional(readOnly = true)
    fun getByProjectId(projectId: Long): List<ProjectFile> {
        return projectFileRepository.findAllByProjectIdOrderByOrderAsc(projectId)
    }

    fun createPresignedUrl(request: ProjectPresignedUrlRequest): PresignedUrlResponse {
        return presignedUrlProviderPort(
            fileName = request.fileName,
            fileSize = request.fileSize,
            contentType = request.contentType,
        )
    }

    fun createPresignedUrls(request: ProjectPresignedUrlBatchRequest): List<PresignedUrlResponse> {
        return request.files.map {
            presignedUrlProviderPort(
                fileName = it.fileName,
                fileSize = it.fileSize,
                contentType = it.contentType,
            )
        }
    }
}