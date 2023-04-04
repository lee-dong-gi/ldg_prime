package com.ldg.prime.maria.master.repository

import com.ldg.prime.maria.master.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
}