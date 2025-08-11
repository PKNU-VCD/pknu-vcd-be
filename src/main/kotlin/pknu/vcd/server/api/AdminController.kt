package pknu.vcd.server.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pknu.vcd.server.application.dto.CreateProjectResponse
import pknu.vcd.server.api.response.ApiResponse
import pknu.vcd.server.application.GuestBookService
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
    private val guestBookService: GuestBookService,
) {

    @GetMapping("/admin/projects")
    fun getAllProjectAdminSummaries(): ResponseEntity<ApiResponse<List<ProjectAdminSummaryDto>>> {
        val response = projectQueryService.getAllProjectAdminSummaries()

        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @GetMapping("/admin/projects/{projectId}")
    fun getProjectsDetails(
        @PathVariable projectId: Long,
    ): ResponseEntity<ApiResponse<ProjectDetailsResponse>> {
        val response = projectQueryService.getProjectDetails(projectId)

        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @PostMapping("/admin/projects")
    fun createProject(
        @RequestBody request: ProjectRequest,
    ): ResponseEntity<ApiResponse<CreateProjectResponse>> {
        val response = projectService.createProject(request)

        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @PutMapping("/admin/projects/{projectId}")
    fun updateProject(
        @PathVariable projectId: Long,
        @RequestBody request: ProjectRequest,
    ): ResponseEntity<ApiResponse<String>> {
        projectService.updateProject(projectId = projectId, request = request)

        val message = "프로젝트(ID: $projectId)가 업데이트되었습니다."
        return ResponseEntity.ok(ApiResponse.success(message))
    }

    @PostMapping("/admin/projects/presigned-url")
    fun createPresignedUrl(
        @RequestBody request: ProjectPresignedUrlRequest,
    ): ResponseEntity<ApiResponse<PresignedUrlResponse>> {
        val response = presignedUrlService.createPresignedUrl(request)

        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @PostMapping("/admin/projects/presigned-url/batch")
    fun createPresignedUrls(
        @RequestBody request: ProjectPresignedUrlBatchRequest,
    ): ResponseEntity<ApiResponse<List<PresignedUrlResponse>>> {
        val response = presignedUrlService.createPresignedUrls(request)

        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @PostMapping("/admin/guestbooks/{guestBookId}/ban")
    fun banGuestBook(
        @PathVariable guestBookId: Long,
    ): ResponseEntity<ApiResponse<BannedGuestBookResponse>> {
        val response = guestBookService.banGuestBook(guestBookId)

        return ResponseEntity.ok(ApiResponse.success(response))
    }
}