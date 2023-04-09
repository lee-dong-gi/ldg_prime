package com.ldg.prime.maria.master.repository

import com.ldg.prime.maria.master.entity.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CompanyRepository : JpaRepository<Company, Long> {
    fun findByComUuid(comUuid: UUID) : Optional<Company>
    fun existsByComUuid(comUuid: UUID) : Boolean
}