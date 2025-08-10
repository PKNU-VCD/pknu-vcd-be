package pknu.vcd.server.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pknu.vcd.server.application.PresignedUrlService
import pknu.vcd.server.application.ProjectQueryService
import pknu.vcd.server.application.ProjectService
import pknu.vcd.server.application.dto.*
import pknu.vcd.server.domain.dto.ProjectAdminSummaryDto

@RestController
class AdminController(
    private val projectService: ProjectService,
    private val projectQueryService: ProjectQueryService,
    private val presignedUrlService: PresignedUrlService,
) {

    @GetMapping("/admin/projects")
    fun getAllProjectAdminSummaries(): ResponseEntity<List<ProjectAdminSummaryDto>> {
        val response = projectQueryService.getAllProjectAdminSummaries()

        return ResponseEntity.ok(response)
    }

    @GetMapping("/admin/projects/{projectId}")
    fun getProjectsDetails(
        @PathVariable projectId: Long,
    ): ResponseEntity<ProjectDetailsResponse> {
        val response = projectQueryService.getProjectDetails(projectId)

        return ResponseEntity.ok(response)
    }

    @PostMapping("/admin/projects")
    fun createProject(
        @RequestBody request: ProjectRequest,
    ): ResponseEntity<String> {
        val projectId = projectService.createProject(request)

        return ResponseEntity.ok("Project created with ID: $projectId")
    }

    @PutMapping("/admin/projects/{projectId}")
    fun updateProject(
        @PathVariable projectId: Long,
        @RequestBody request: ProjectRequest,
    ): ResponseEntity<String> {
        projectService.updateProject(projectId = projectId, request = request)

        return ResponseEntity.ok("Project updated")
    }

    @PostMapping("/admin/projects/presigned-url")
    fun createPresignedUrl(
        @RequestBody request: ProjectPresignedUrlRequest,
    ): PresignedUrlResponse {
        return presignedUrlService.createPresignedUrl(request)
    }

    @PostMapping("/admin/projects/presigned-url/batch")
    fun createPresignedUrls(
        @RequestBody request: ProjectPresignedUrlBatchRequest,
    ): List<PresignedUrlResponse> {
        return presignedUrlService.createPresignedUrls(request)
    }
}