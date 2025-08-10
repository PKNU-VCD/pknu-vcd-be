package pknu.vcd.server.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pknu.vcd.server.domain.dto.GuestBookEntryDto
import java.time.LocalDateTime

@Repository
interface GuestBookEntryRepository : JpaRepository<GuestBookEntry, Long> {

    @Query(
        """
        SELECT new pknu.vcd.server.domain.dto.GuestBookEntryDto(
            gbe.id,
            gbe.content,
            gbe.createdAt
        )
        FROM GuestBookEntry gbe
        WHERE gbe.banned = false
        ORDER BY gbe.createdAt DESC
    """
    )
    fun findAllByBannedFalse(): List<GuestBookEntryDto>

    fun findTopByClientIpAndCreatedAtAfterOrderByCreatedAtDesc(
        clientIp: String,
        createdAt: LocalDateTime,
    ): GuestBookEntry?
}