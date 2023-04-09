package com.ldg.prime.v1.maria.company.service

import com.ldg.prime.v1.common.GlobalConstant
import com.ldg.prime.v1.maria.company.dto.request.CompanyRegRequest
import com.ldg.prime.v1.maria.company.repositoty.CompanyRepository
import com.ldg.prime.v1.maria.entity.Company
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CompanyService(
    private val companyRepository: CompanyRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun findAll(): List<Company> {
        return companyRepository.findAll()
    }

    @Transactional(transactionManager = GlobalConstant.WRITE_ONLY_TM)
    fun register(request: CompanyRegRequest) {
        log.info("{} company reg req", request)
        when (companyRepository.existsByComUuid(request.comUuid)) {
            true -> {
                throw UsernameNotFoundException("업체가 이미 존재합니다.")
            }

            false -> {
                companyRepository.save(Company(request))
            }
        }
    }
}