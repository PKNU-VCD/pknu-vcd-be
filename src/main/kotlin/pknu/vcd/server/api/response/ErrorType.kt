package pknu.vcd.server.api.response

import org.springframework.http.HttpStatus

enum class ErrorType(
    val status: HttpStatus,
    val message: String,
) {
    // common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다. 잠시 후 다시 시도해주세요."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 요청입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다."),
    NO_RESOURCE_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),

    // auth
    BAD_CREDENTIALS(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 잘못되었습니다."),
    ALREADY_SESSION_EXISTS(HttpStatus.BAD_REQUEST, "이미 로그인된 세션이 존재합니다."),

    // project
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 프로젝트입니다."),
    PROJECT_FILE_DISPLAY_ORDER_NOT_VALID(HttpStatus.BAD_REQUEST, "프로젝트 파일들의 표시 순서는 1부터 시작하는 연속된 형식이어야 합니다."),

    // guest book
    GUEST_BOOK_RATE_LIMIT_EXCEEDED(HttpStatus.TOO_MANY_REQUESTS, "방명록 작성에 대한 요청이 너무 많습니다. 잠시 후 다시 시도해주세요."),
    GUEST_BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 방명록입니다."),
}