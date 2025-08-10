package pknu.vcd.server.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pknu.vcd.server.domain.Admin

@Repository
interface AdminRepository : JpaRepository<Admin, Long> {

    fun findByUsername(username: String): Admin?
}
