package pknu.vcd.server.application.exception

import pknu.vcd.server.domain.BaseException

class ProjectFileDisplayOrderNotValidException : BaseException("프로젝트 파일들의 표시 순서는 1부터 시작하는 연속된 형식이어야 합니다.")