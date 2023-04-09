package com.ldg.prime.v1.maria.company.repositoty

import com.ldg.prime.v1.maria.entity.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CompanyRepository : JpaRepository<Company, Long> {
    fun findByComUuid(comUuid: UUID): Optional<Company>
    fun existsByComUuid(comUuid: UUID): Boolean
}