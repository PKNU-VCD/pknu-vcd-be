package pknu.vcd.server.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import pknu.vcd.server.domain.dto.ProjectSummaryDto

interface ProjectRepository : JpaRepository<Project, Long> {

    @Query(
        """
        SELECT new pknu.vcd.server.domain.dto.ProjectSummaryDto(
            project.id,
            project.designerNameKr,
            project.projectNameKr,
            project.createdAt
        )
        FROM Project project
        ORDER BY project.createdAt DESC
        """
    )
    fun findAllProjectSummaries(): List<ProjectSummaryDto>
}