package pknu.vcd.server.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface ProjectFileRepository : JpaRepository<ProjectFile, Long> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM ProjectFile projectFile WHERE projectFile.projectId = :projectId")
    fun deleteByProjectId(projectId: Long)

    fun findAllByProjectIdOrderByOrderAsc(projectId: Long): List<ProjectFile>
}