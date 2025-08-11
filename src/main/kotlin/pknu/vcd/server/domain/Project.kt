package pknu.vcd.server.domain

import jakarta.persistence.*
import pknu.vcd.server.domain.dto.ProjectUpdateCommand

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

    fun update(command: ProjectUpdateCommand) {
        this.designerEmail = command.designerEmail
        this.designerNameKr = command.designerNameKr
        this.designerNameEn = command.designerNameEn
        this.projectNameKr = command.projectNameKr
        this.projectNameEn = command.projectNameEn
        this.descriptionKr = command.descriptionKr
        this.descriptionEn = command.descriptionEn
        this.thumbnailUrl = command.thumbnailUrl
        this.categoriesString = command.categoriesString

        updateTimestamps()
    }
}