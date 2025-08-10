package pknu.vcd.server.domain

import jakarta.persistence.*

@Entity
class Admin(
    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false)
    val password: String,
) : AuditableEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}