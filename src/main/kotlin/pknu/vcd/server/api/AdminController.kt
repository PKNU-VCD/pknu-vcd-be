package pknu.vcd.server.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pknu.vcd.server.application.ProjectFileService
import pknu.vcd.server.application.ProjectService
import pknu.vcd.server.application.dto.*
import pknu.vcd.server.domain.dto.ProjectSummaryDto

@RestController
class AdminController(
    private val projectService: ProjectService,
    private val projectFileService: ProjectFileService,
) {

    @GetMapping("/admin/projects")
    fun getProjectSummaries(): ResponseEntity<List<ProjectSummaryDto>> {
        val response = projectService.getAllProjectSummaries()

        return ResponseEntity.ok(response)
    }

    @GetMapping("/admin/projects/{projectId}")
    fun getProjectsDetails(
        @PathVariable projectId: Long,
    ): ResponseEntity<ProjectDetailsResponse> {
        val response = projectService.getProjectDetails(projectId)

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
        return projectFileService.createPresignedUrl(request)
    }

    @PostMapping("/admin/projects/presigned-url/batch")
    fun createPresignedUrls(
        @RequestBody request: ProjectPresignedUrlBatchRequest,
    ): List<PresignedUrlResponse> {
        return projectFileService.createPresignedUrls(request)
    }
}