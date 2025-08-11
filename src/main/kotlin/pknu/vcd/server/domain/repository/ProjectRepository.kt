package pknu.vcd.server.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import pknu.vcd.server.domain.Project
import pknu.vcd.server.domain.dto.ProjectAdminSummaryDto
import pknu.vcd.server.domain.dto.ProjectPublicSummaryDto

interface ProjectRepository : JpaRepository<Project, Long> {

    @Query(
        """
        SELECT new pknu.vcd.server.domain.dto.ProjectPublicSummaryDto(
            project.id,
            project.designerNameKr,
            project.projectNameKr,
            project.thumbnailUrl,
            project.categoriesString
        )
        FROM Project project
        """
    )
    fun findAllProjectPublicSummaries(): List<ProjectPublicSummaryDto>

    @Query(
        """
        SELECT new pknu.vcd.server.domain.dto.ProjectAdminSummaryDto(
            project.id,
            project.designerNameKr,
            project.projectNameKr,
            project.createdAt,
            project.updatedAt
        )
        FROM Project project
        ORDER BY project.updatedAt DESC
        """
    )
    fun findAllProjectAdminSummaries(): List<ProjectAdminSummaryDto>
}