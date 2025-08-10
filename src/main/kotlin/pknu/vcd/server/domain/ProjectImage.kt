package pknu.vcd.server.domain

import jakarta.persistence.*

@Entity
class ProjectImage(
    @Column(nullable = false)
    val filePath: String,

    @Column(nullable = false)
    val originalFileName: String,

    @Column(nullable = false)
    val displayOrder: Int,

    @Column(nullable = false)
    val isThumbnail: Boolean,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}