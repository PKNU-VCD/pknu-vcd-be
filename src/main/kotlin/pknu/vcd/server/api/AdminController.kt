package pknu.vcd.server.api

import org.springframework.web.bind.annotation.*
import pknu.vcd.server.application.dto.RegisterProjectRequest

@RestController
class AdminController {

    @PostMapping("/admin/projects")
    fun createProject(
        @RequestBody request: RegisterProjectRequest,
    ) {
    }

    @PutMapping("/admin/projects/{projectId}")
    fun updateProject(
        @PathVariable projectId: String,
        @RequestBody request: RegisterProjectRequest,
    ) {
    }
}