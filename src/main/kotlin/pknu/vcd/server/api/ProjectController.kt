package pknu.vcd.server.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pknu.vcd.server.application.ProjectFileService
import pknu.vcd.server.application.ProjectService
import pknu.vcd.server.application.dto.PresignedUrlResponse
import pknu.vcd.server.application.dto.ProjectDetailsResponse
import pknu.vcd.server.application.dto.ProjectPresignedUrlBatchRequest
import pknu.vcd.server.application.dto.ProjectPresignedUrlRequest

@RestController
class ProjectController(
    private val projectService: ProjectService,
    private val projectFileService: ProjectFileService,
) {

    @GetMapping("/projects")
    fun getProjects() {
        // TODO: 프로젝트 목록
    }

    @GetMapping("/projects/{projectId}")
    fun getProjectDetails(
        @PathVariable projectId: Long,
    ): ResponseEntity<ProjectDetailsResponse> {
        val response = projectService.getProjectDetails(projectId)

        return ResponseEntity.ok(response)
    }

    @PostMapping("/projects/presigned-url")
    fun createPresignedUrl(
        @RequestBody request: ProjectPresignedUrlRequest,
    ): PresignedUrlResponse {
        return projectFileService.createPresignedUrl(request)
    }

    @PostMapping("/projects/presigned-url/batch")
    fun createPresignedUrls(
        @RequestBody request: ProjectPresignedUrlBatchRequest,
    ): List<PresignedUrlResponse> {
        return projectFileService.createPresignedUrls(request)
    }
}