package pknu.vcd.server.api.advice

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException
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

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ApiResponse<Unit>> {
        val cause = ex.cause

        if (cause is InvalidFormatException && cause.targetType.isEnum) {
            val invalidValue = cause.value
            val enumType = cause.targetType.simpleName
            val message = "잘못된 값 '$invalidValue' 가 $enumType 타입에서 허용되지 않습니다."

            val errorDetails = listOf(ApiErrorDetail(field = "enum", message = message))
            val apiResponse = ApiResponse.error(ErrorType.BAD_REQUEST, errorDetails)

            return ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST)
        }

        val apiResponse = ApiResponse.error(ErrorType.BAD_REQUEST)
        return ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(e: NoResourceFoundException): ResponseEntity<ApiResponse<Unit>> {
        log.warn("[NoResourceFoundException] {}", e.message, e)

        val errorType = ErrorType.NO_RESOURCE_FOUND
        val response = ApiResponse.error(errorType)

        return ResponseEntity(response, errorType.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Unit>> {
        log.error("[Exception] {}", e.message, e)

        val errorType = ErrorType.INTERNAL_SERVER_ERROR
        val response = ApiResponse.error(errorType)

        return ResponseEntity(response, errorType.status)
    }
}