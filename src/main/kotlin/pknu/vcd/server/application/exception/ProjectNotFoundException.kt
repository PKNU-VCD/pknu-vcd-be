package pknu.vcd.server.application.exception

import pknu.vcd.server.domain.BaseException

class ProjectNotFoundException(projectId: Long) : BaseException("프로젝트(ID: $projectId)를 찾을 수 없습니다.")