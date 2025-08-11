package pknu.vcd.server.application.exception

import pknu.vcd.server.domain.BaseException

class GuestBookNotFoundException : BaseException("방명록을 찾을 수 없습니다.")