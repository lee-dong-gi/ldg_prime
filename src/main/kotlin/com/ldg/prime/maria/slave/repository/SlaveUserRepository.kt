package com.ldg.prime.maria.slave.repository

import com.ldg.prime.maria.slave.entity.R_User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
interface SlaveUserRepository : JpaRepository<R_User, Long> {
}