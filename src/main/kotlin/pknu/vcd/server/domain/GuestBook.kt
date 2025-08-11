package pknu.vcd.server.domain

import jakarta.persistence.*

@Entity
class GuestBook(
    @Column(nullable = false)
    val content: String,

    @Column(nullable = false)
    val clientIp: String,

    @Column(nullable = false)
    var banned: Boolean = false,
) : AuditableEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    fun ban() {
        this.banned = true
    }
}