package pknu.vcd.server.application

import org.springframework.stereotype.Service
import pknu.vcd.server.domain.ProjectRepository

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
)