package pknu.vcd.server.infra.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.web.filter.OncePerRequestFilter

class ClientIpLoggingFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val ip = getClientIp(request)
            MDC.put("clientIp", ip)
            filterChain.doFilter(request, response)
        } finally {
            MDC.remove("clientIp")
        }
    }

    private fun getClientIp(request: HttpServletRequest): String {
        val xfHeader = request.getHeader("X-Forwarded-For")
        if (xfHeader.isNullOrBlank()) {
            return request.remoteAddr
        }
        return xfHeader.split(",").first().trim()
    }
}