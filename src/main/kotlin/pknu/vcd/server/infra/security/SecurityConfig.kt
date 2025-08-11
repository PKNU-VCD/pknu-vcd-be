package pknu.vcd.server.infra.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.session.SessionAuthenticationException
import org.springframework.security.web.session.HttpSessionEventPublisher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import pknu.vcd.server.api.response.ApiResponse
import pknu.vcd.server.api.response.ErrorType

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val objectMapper: ObjectMapper,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val clientIpLoggingFilter = ClientIpLoggingFilter()

        http
            .userDetailsService(userDetailsService)
            .csrf { it.disable() }
            .cors { }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/login", "/logout").permitAll()
                    .requestMatchers("/admin/**").authenticated()
                    .anyRequest().permitAll()
            }
            .formLogin {
                it
                    .loginProcessingUrl("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler { _, response, _ ->
                        val apiResponse = ApiResponse.success("로그인에 성공했습니다.")
                        writeResponse(response, HttpStatus.OK, objectMapper.writeValueAsString(apiResponse))
                    }
                    .failureHandler { _, response, ex ->
                        log.warn("[Login Authentication Exception] {}", ex.message, ex)

                        val errorType = when (ex) {
                            is BadCredentialsException -> ErrorType.BAD_CREDENTIALS
                            is SessionAuthenticationException -> ErrorType.ALREADY_SESSION_EXISTS
                            else -> ErrorType.UNAUTHORIZED
                        }
                        val apiResponse = ApiResponse.error(errorType)
                        writeResponse(response, errorType.status, objectMapper.writeValueAsString(apiResponse))
                    }
            }
            .logout {
                it
                    .logoutUrl("/logout")
                    .logoutSuccessHandler { _, response, _ ->
                        val apiResponse = ApiResponse.success("로그아웃에 성공했습니다.")
                        writeResponse(response, HttpStatus.OK, objectMapper.writeValueAsString(apiResponse))
                    }
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
            }
            .exceptionHandling {
                it
                    .authenticationEntryPoint { _, response, _ ->
                        val errorType = ErrorType.UNAUTHORIZED
                        val apiResponse = ApiResponse.error(errorType)
                        writeResponse(response, errorType.status, objectMapper.writeValueAsString(apiResponse))
                    }
                    .accessDeniedHandler { _, response, _ ->
                        val errorType = ErrorType.FORBIDDEN
                        val apiResponse = ApiResponse.error(errorType)
                        writeResponse(response, errorType.status, objectMapper.writeValueAsString(apiResponse))
                    }
            }
            .addFilterBefore(clientIpLoggingFilter, UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement {
                it
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
            }

        return http.build()
    }

    @Bean
    fun httpSessionEventPublisher(): HttpSessionEventPublisher {
        return HttpSessionEventPublisher()
    }

    @Bean
    fun sessionRegistry(): SessionRegistry {
        return SessionRegistryImpl()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowedOrigins = listOf("http://localhost:3000", "http://localhost:5173")
        corsConfiguration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        corsConfiguration.allowedHeaders = listOf("*")
        corsConfiguration.allowCredentials = true
        corsConfiguration.maxAge = 3600L // 1 hour

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)

        return source
    }

    private fun writeResponse(response: HttpServletResponse, status: HttpStatus, responseBody: String) {
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = status.value()
        response.writer.write(responseBody)
    }
}