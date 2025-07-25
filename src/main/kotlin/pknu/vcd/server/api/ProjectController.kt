package pknu.vcd.server.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/projects")
class ProjectController {

    @GetMapping("/")
    fun getProjects(): String {
        return "List of projects"
    }
}