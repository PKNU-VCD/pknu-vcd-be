package pknu.vcd.server

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pknu.vcd.server.core.Admin
import pknu.vcd.server.core.AdminRepository

@Component
class DataInitializer(
    private val adminRepository: AdminRepository,
    private val passwordEncoder: PasswordEncoder,
) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments?) {
        val username = "admin"
        val password = "password"

        val hashedPassword = passwordEncoder.encode(password)
        val admin = Admin(username = username, password = hashedPassword)
        adminRepository.save(admin)
    }
}