package com.ldg.prime.maria.controller

import com.ldg.prime.maria.aop.PermitAuthority
import com.ldg.prime.maria.common.Authority
import com.ldg.prime.maria.dto.request.CompanyRegRequest
import com.ldg.prime.maria.master.service.CompanyService
import com.ldg.prime.maria.master.service.UserService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/company")
class CompanyController (val companyService: CompanyService){
    private val log = LoggerFactory.getLogger(javaClass)

    @PermitAuthority(Authority.GM)
    @PostMapping
    fun register(@Valid @RequestBody request: CompanyRegRequest):ResponseEntity<Any?>{
        return ResponseEntity<Any?>(companyService.register(request), HttpStatus.OK)
    }

}