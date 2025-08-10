package pknu.vcd.server.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pknu.vcd.server.domain.GuestBook
import pknu.vcd.server.domain.dto.GuestBookSummaryDto

@Repository
interface GuestBookRepository : JpaRepository<GuestBook, Long> {

    @Query(
        """
        SELECT new pknu.vcd.server.domain.dto.GuestBookSummaryDto(
            guestBook.id,
            guestBook.content,
            guestBook.createdAt
        )
        FROM GuestBook guestBook
        WHERE guestBook.banned = false
        ORDER BY guestBook.createdAt DESC
        """
    )
    fun findAllByBannedFalse(): List<GuestBookSummaryDto>
}