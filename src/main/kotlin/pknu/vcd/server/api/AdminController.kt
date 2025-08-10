package pknu.vcd.server.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pknu.vcd.server.application.ProjectService
import pknu.vcd.server.application.dto.ProjectRequest
import pknu.vcd.server.domain.dto.ProjectSummaryDto

@RestController
class AdminController(
    private val projectService: ProjectService,
) {

    @GetMapping("/admin/projects")
    fun getProjects(): ResponseEntity<List<ProjectSummaryDto>> {
        val response = projectService.getAllProjectSummaries()

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
}