package pknu.vcd.server.domain

import jakarta.persistence.*

@Entity
class Project(
    @Column(nullable = false)
    val designerName: String,

    @Column(nullable = false)
    val designerEmail: String,

    @Column(nullable = false)
    val designerNameKr: String,

    val designerNameEn: String,

    @Column(nullable = false)
    val projectNameKr: String,

    val projectNameEn: String,

    @Column(nullable = false)
    val descriptionKr: String,

    val descriptionEn: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val projectType: ProjectType,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}