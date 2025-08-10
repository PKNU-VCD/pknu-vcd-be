package pknu.vcd.server.domain

import jakarta.persistence.*

@Entity
class ProjectFile(
    @Column(nullable = false)
    val projectId: Long,

    @Column(nullable = false)
    val fileUrl: String,

    @Column(nullable = false)
    val displayOrder: Int,
) : AuditableEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}