package com.ldg.prime.maria.master.service

import com.ldg.prime.config.JpaConfig
import com.ldg.prime.maria.master.entity.User
import com.ldg.prime.maria.master.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.datasource.lookup.DataSourceLookup
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

@Service
class UserService {
    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var userRepository: UserRepository

    @Transactional(readOnly = true)
    fun findAll(): List<User>{
        log.error("{} service find isCurrentTransactionReadOnly", TransactionSynchronizationManager.isCurrentTransactionReadOnly().toString())
        return userRepository.findAll()
    }

    @Transactional(readOnly = false)
    fun save(){
        log.error("{} service save isCurrentTransactionReadOnly", TransactionSynchronizationManager.isCurrentTransactionReadOnly().toString())
        val user = User(null,"utest2","utest22")
        userRepository.save(user)
    }
}