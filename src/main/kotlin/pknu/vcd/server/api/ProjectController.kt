package pknu.vcd.server.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pknu.vcd.server.api.response.ApiResponse
import pknu.vcd.server.application.ProjectQueryService
import pknu.vcd.server.application.dto.ProjectDetailsResponse
import pknu.vcd.server.application.dto.ProjectPublicSummaryResponse

@RestController
class ProjectController(
    private val projectQueryService: ProjectQueryService,
) {

    @GetMapping("/projects")
    fun getAllProjectPublicSummaries(): ResponseEntity<ApiResponse<List<ProjectPublicSummaryResponse>>> {
        val response = projectQueryService.getAllProjectPublicSummaries()

        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @GetMapping("/projects/{projectId}")
    fun getProjectDetails(
        @PathVariable projectId: Long,
    ): ResponseEntity<ApiResponse<ProjectDetailsResponse>> {
        val response = projectQueryService.getProjectDetails(projectId)

        return ResponseEntity.ok(ApiResponse.success(response))
    }
}