package pknu.vcd.server.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pknu.vcd.server.application.ProjectService
import pknu.vcd.server.application.dto.ProjectDetailsResponse

@RestController
class ProjectController(
    private val projectService: ProjectService,
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
}