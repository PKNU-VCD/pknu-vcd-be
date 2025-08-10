package pknu.vcd.server.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pknu.vcd.server.application.ProjectQueryService
import pknu.vcd.server.application.dto.ProjectDetailsResponse
import pknu.vcd.server.application.dto.ProjectPublicSummaryResponse

@RestController
class ProjectController(
    private val projectQueryService: ProjectQueryService,
) {

    @GetMapping("/projects")
    fun getAllProjectPublicSummaries(): ResponseEntity<List<ProjectPublicSummaryResponse>> {
        val response = projectQueryService.getAllProjectPublicSummaries()

        return ResponseEntity.ok(response)
    }

    @GetMapping("/projects/{projectId}")
    fun getProjectDetails(
        @PathVariable projectId: Long,
    ): ResponseEntity<ProjectDetailsResponse> {
        val response = projectQueryService.getProjectDetails(projectId)

        return ResponseEntity.ok(response)
    }
}