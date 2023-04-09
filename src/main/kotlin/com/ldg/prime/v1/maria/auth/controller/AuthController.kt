package com.ldg.prime.v1.maria.auth.controller

import com.ldg.prime.v1.maria.auth.dto.request.UserSignInRequest
import com.ldg.prime.v1.maria.auth.dto.request.UserSignUpRequest
import com.ldg.prime.v1.maria.auth.enums.Authority
import com.ldg.prime.v1.common.aop.PermitAuthority
import com.ldg.prime.v1.common.dto.ListNonPageResult
import com.ldg.prime.v1.common.dto.ResultUtil
import com.ldg.prime.v1.maria.entity.User
import com.ldg.prime.v1.maria.user.service.UserService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController (val userService: UserService){
    private val log = LoggerFactory.getLogger(javaClass)

    @PermitAuthority(Authority.CU)
    @GetMapping("/users")
    fun findAll():ResponseEntity<ListNonPageResult<Any>>{
        return ResponseEntity.ok(ResultUtil.success(userService.findAll()))
    }

    @PostMapping("/sign-in")
    fun signIn(@Valid @RequestBody request: UserSignInRequest):ResponseEntity<Any?>{
        return ResponseEntity.ok(ResultUtil.success(userService.signIn(request)))
    }

    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody request: UserSignUpRequest):ResponseEntity<Any?>{
        userService.signUp(request)
        return ResponseEntity.ok(ResultUtil.success())
    }

}