package pknu.vcd.server.domain

abstract class BaseException(
    message: String,
    throwable: Throwable? = null,
) : RuntimeException(message, throwable)