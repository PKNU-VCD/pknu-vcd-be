package pknu.vcd.server.core

import jakarta.persistence.*

@Entity
class Admin(
    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false)
    val password: String,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}