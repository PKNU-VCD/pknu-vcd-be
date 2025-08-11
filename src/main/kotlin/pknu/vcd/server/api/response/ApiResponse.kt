package pknu.vcd.server.api.response

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: ErrorMessage? = null,
) {

    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(success = true, data = data, error = null)
        }

        fun error(errorType: ErrorType, errorData: Any? = null): ApiResponse<Unit> {
            return ApiResponse(
                success = false,
                data = null,
                error = ErrorMessage(errorType, errorData)
            )
        }
    }
}
