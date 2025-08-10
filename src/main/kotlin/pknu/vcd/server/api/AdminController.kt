package pknu.vcd.server.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pknu.vcd.server.application.dto.RegisterProjectRequest

@RestController
class AdminController {

    @GetMapping("/admin")
    fun test(): String {
        return "Hello, Admin!"
    }

    @PostMapping("/admin/projects")
    fun createProject(
        @RequestBody request: RegisterProjectRequest
    ) {}
}