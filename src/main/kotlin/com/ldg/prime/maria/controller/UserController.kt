package com.ldg.prime.maria.controller

import com.ldg.prime.maria.aop.PermitAuthority
import com.ldg.prime.maria.common.Authority
import com.ldg.prime.maria.dto.request.UserSignInRequest
import com.ldg.prime.maria.dto.request.UserSignUpRequest
import com.ldg.prime.maria.master.service.UserService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class UserController (val userService: UserService){
    private val log = LoggerFactory.getLogger(javaClass)

    @PermitAuthority(Authority.DM)
    @GetMapping("/users")
    fun findAll():ResponseEntity<Any?>{
        return ResponseEntity<Any?>(userService.findAll(), HttpStatus.OK)
    }

    @PostMapping("/sign-in")
    fun signIn(@Valid @RequestBody request: UserSignInRequest):ResponseEntity<Any?>{
        return ResponseEntity<Any?>(userService.signIn(request), HttpStatus.OK)
    }

    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody request: UserSignUpRequest):ResponseEntity<Any?>{
        return ResponseEntity<Any?>(userService.signUp(request), HttpStatus.OK)
    }

}