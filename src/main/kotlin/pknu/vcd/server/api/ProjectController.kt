package pknu.vcd.server.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pknu.vcd.server.application.ProjectService

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
    ) {
    }
}