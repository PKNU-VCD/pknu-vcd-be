package pknu.vcd.server.api

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import pknu.vcd.server.api.response.ApiErrorDetail
import pknu.vcd.server.api.response.ApiResponse
import pknu.vcd.server.api.response.ErrorType
import pknu.vcd.server.application.exception.*
import pknu.vcd.server.domain.BaseException

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(GuestBookRateLimitExceededException::class)
    fun handleGuestBookRateLimitExceededException(e: GuestBookRateLimitExceededException): ResponseEntity<ApiResponse<Unit>> {
        log.warn("[GuestBookRateLimitExceededException] {}", e.message, e)

        val errorType = ErrorType.GUEST_BOOK_RATE_LIMIT_EXCEEDED
        val response = ApiResponse.error(errorType, RemainingSeconds(e.remainingSeconds))

        return ResponseEntity(response, errorType.status)
    }

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(e: BaseException): ResponseEntity<ApiResponse<Unit>> {
        log.warn("[BaseException] {}", e.message, e)

        val errorType = when (e) {
            is ProjectNotFoundException -> ErrorType.PROJECT_NOT_FOUND
            is ProjectFileDisplayOrderNotValidException -> ErrorType.PROJECT_FILE_DISPLAY_ORDER_NOT_VALID
            is GuestBookNotFoundException -> ErrorType.GUEST_BOOK_NOT_FOUND
            else -> ErrorType.INTERNAL_SERVER_ERROR
        }
        val response = ApiResponse.error(errorType)

        return ResponseEntity(response, errorType.status)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Unit>> {
        log.warn("MethodArgumentNotValidException : {}", e.message, e)

        val errorDetails = e.bindingResult.fieldErrors.map { error ->
            ApiErrorDetail(field = error.field, message = error.defaultMessage ?: "유효하지 않은 값입니다.")
        }

        val response = ApiResponse.error(ErrorType.BAD_REQUEST, errorDetails)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Unit>> {
        log.error("[Exception] {}", e.message, e)

        val errorType = ErrorType.INTERNAL_SERVER_ERROR
        val response = ApiResponse.error(errorType)

        return ResponseEntity(response, errorType.status)
    }
}