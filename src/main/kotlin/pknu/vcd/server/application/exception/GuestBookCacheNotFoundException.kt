package pknu.vcd.server.application.exception

import pknu.vcd.server.domain.BaseException

class GuestBookCacheNotFoundException
    : BaseException(message = "Guest Book 캐시가 존재하지 않습니다.")
