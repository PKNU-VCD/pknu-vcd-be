package pknu.vcd.server.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
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
            // new FormData() 처리
            .formLogin {
                it
                    .loginProcessingUrl("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler { _, response, _ ->
                        response.status = 200
                        response.writer.write("Login successful")
                    }
                    .failureHandler { _, response, ex ->
                        response.status = 401
                        response.writer.write("Login failed: ${ex.message}")
                    }
            }
            .logout {
                it
                    .logoutUrl("/logout")
                    .logoutSuccessHandler { _, response, _ ->
                        response.status = 200
                        response.writer.write("Logout successful")
                    }
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
            }
            .exceptionHandling {
                it
                    .authenticationEntryPoint { _, response, authException ->
                        response.status = 401
                        response.writer.write("Unauthorized: ${authException.message}")
                    }
                    .accessDeniedHandler { _, response, accessDeniedException ->
                        response.status = 403
                        response.writer.write("Forbidden: ${accessDeniedException.message}")
                    }
            }
            .sessionManagement {
                it
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
            }

        return http.build()
    }

    @Bean
    fun corsConfiguration(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration().apply {
            allowedOrigins = listOf("*") // temp
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
            allowedHeaders = listOf("*")
            allowCredentials = true
            maxAge = 3600L // 1 hour
        }

        val configurationSource = UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", corsConfiguration)
        }

        return configurationSource
    }
}