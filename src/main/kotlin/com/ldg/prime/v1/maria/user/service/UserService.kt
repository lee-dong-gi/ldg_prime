package com.ldg.prime.v1.maria.user.service

import com.ldg.prime.v1.maria.auth.dto.request.UserSignInRequest
import com.ldg.prime.v1.maria.auth.dto.request.UserSignUpRequest
import com.ldg.prime.v1.common.GlobalConstant.READ_ONLY_TM
import com.ldg.prime.v1.common.GlobalConstant.WRITE_ONLY_TM
import com.ldg.prime.v1.common.utils.JwtUtils
import com.ldg.prime.v1.maria.company.repositoty.CompanyRepository
import com.ldg.prime.v1.maria.entity.Company
import com.ldg.prime.v1.maria.entity.User
import com.ldg.prime.v1.maria.user.repositoty.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val companyRepository: CompanyRepository,
    private val jwtUtils: JwtUtils
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional(readOnly = true, transactionManager = READ_ONLY_TM)
    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    @Transactional(transactionManager = WRITE_ONLY_TM)
    fun signUp(request: UserSignUpRequest) {
        log.info("{} user reg req", request)
        when (userRepository.existsByUserId(request.userId)) {
            true -> {
                throw UsernameNotFoundException("userId가 이미 존재합니다.")
            }

            false -> {
                userRepository.save(User(request, findByComUuid(request.comUuid)))
            }
        }
    }

    @Transactional(readOnly = true, transactionManager = READ_ONLY_TM)
    fun findByComUuid(uuid: UUID): Company {
        return companyRepository.findByComUuid(uuid)
            .orElseThrow { throw UsernameNotFoundException("존재하지 않는 업체 입니다.") }
    }

    @Transactional(readOnly = true, transactionManager = READ_ONLY_TM)
    fun signIn(request: UserSignInRequest): ResponseEntity<Any?> {
        log.info("{} user reg req", request)
        val user: User = userRepository.findByUserId(request.userId)
            .orElseThrow { throw UsernameNotFoundException("존재하지 않는 userId 입니다.") }
        when (passwordEncoder.matches(request.userPassword, user.userPassword)) {
            true -> {
                return ResponseEntity<Any?>(jwtUtils.createToken(request.userId), HttpStatus.OK)
            }

            false -> {
                throw UsernameNotFoundException("패스워드가 일치하지 않습니다.")
            }
        }
    }
}