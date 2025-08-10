package pknu.vcd.server.domain

import jakarta.persistence.*

@Entity
class Project(
    @Column(nullable = false)
    var designerEmail: String,

    @Column(nullable = false)
    var designerNameKr: String,

    @Column(nullable = false)
    var designerNameEn: String,

    @Column(nullable = false)
    var projectNameKr: String,

    @Column
    var projectNameEn: String?,

    @Column(nullable = false)
    var descriptionKr: String,

    @Column
    var descriptionEn: String?,

    @Column(nullable = false)
    var thumbnailUrl: String,

    @Column(nullable = false)
    var categoriesString: String,
) : AuditableEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}