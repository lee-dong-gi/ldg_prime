package com.ldg.prime.maria.master.repository

import com.ldg.prime.maria.master.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    fun findByUsername(username: String) : Member?
}