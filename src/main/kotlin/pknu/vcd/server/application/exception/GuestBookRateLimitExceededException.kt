package pknu.vcd.server.application.exception

import pknu.vcd.server.domain.BaseException

class GuestBookRateLimitExceededException(val remainingSeconds: Long) :
    BaseException("방명록 작성에 대한 요청이 너무 많습니다. 잠시 후 다시 시도해주세요. 남은 시간: $remainingSeconds 초")

data class RemainingSeconds(
    val remainingSeconds: Long,
)