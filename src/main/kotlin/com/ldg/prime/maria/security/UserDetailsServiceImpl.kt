package com.ldg.prime.maria.security

import com.ldg.prime.maria.master.entity.User
import com.ldg.prime.maria.master.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails {
        val user: User = userRepository.findByUserId(userId).orElseThrow{ throw UsernameNotFoundException("존재하지 않는 userId 입니다.") }
        return UserDetailsImpl(user)
    }
}