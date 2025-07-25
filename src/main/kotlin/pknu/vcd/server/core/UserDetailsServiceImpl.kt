package pknu.vcd.server.core

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val adminRepository: AdminRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = adminRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        return User.builder()
            .username(user.username)
            .password(user.password)
            .roles("ADMIN")
            .build()
    }
}