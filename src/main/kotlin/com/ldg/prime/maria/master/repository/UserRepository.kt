package com.ldg.prime.maria.master.repository

import com.ldg.prime.maria.master.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUserId(userId: String) : Optional<User>
    fun existsByUserId(userId: String) : Boolean
}