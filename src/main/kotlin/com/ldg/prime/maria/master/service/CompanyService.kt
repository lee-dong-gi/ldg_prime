package com.ldg.prime.maria.master.service

import com.ldg.prime.maria.common.GlobalConstant
import com.ldg.prime.maria.dto.request.CompanyRegRequest
import com.ldg.prime.maria.master.entity.Company
import com.ldg.prime.maria.master.repository.CompanyRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CompanyService (
    private val companyRepository: CompanyRepository
){
    private val log = LoggerFactory.getLogger(javaClass)

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Transactional(transactionManager = GlobalConstant.WRITE_ONLY_TM)
    fun register(request: CompanyRegRequest){
        log.info("{} company reg req", request)
        when (companyRepository.existsByComUuid(request.comUuid)) {
            true -> {
                throw UsernameNotFoundException("업체가 이미 존재합니다.")
            }
            false -> {
                companyRepository.save(Company(request))
                entityManager.flush()
                entityManager.clear()
            }
        }
    }
}