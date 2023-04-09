package com.ldg.prime.v1.security

import com.ldg.prime.v1.maria.entity.User
import com.ldg.prime.v1.maria.user.repositoty.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails {
        val user: User =
            userRepository.findByUserId(userId).orElseThrow { throw UsernameNotFoundException("존재하지 않는 userId 입니다.") }
        return UserDetailsImpl(user)
    }
}