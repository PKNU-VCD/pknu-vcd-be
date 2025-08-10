package pknu.vcd.server.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import pknu.vcd.server.domain.ProjectFile

interface ProjectFileRepository : JpaRepository<ProjectFile, Long> {

    @Modifying // clearAutomatically = true 은 Project 변경 감지를 위해 주석 처리
    @Query("DELETE FROM ProjectFile projectFile WHERE projectFile.projectId = :projectId")
    fun deleteByProjectId(projectId: Long)

    fun findAllByProjectIdOrderByDisplayOrderAsc(projectId: Long): List<ProjectFile>
}