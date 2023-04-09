package com.ldg.prime.v1.maria.user.repositoty

import com.ldg.prime.v1.maria.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUserId(userId: String): Optional<User>
    fun existsByUserId(userId: String): Boolean
}