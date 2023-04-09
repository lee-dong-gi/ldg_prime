package com.ldg.prime.v1.maria.company.controller

import com.ldg.prime.v1.maria.auth.enums.Authority
import com.ldg.prime.v1.common.aop.PermitAuthority
import com.ldg.prime.v1.common.dto.ResultUtil
import com.ldg.prime.v1.maria.company.dto.request.CompanyRegRequest
import com.ldg.prime.v1.maria.company.service.CompanyService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/company")
class CompanyController(val companyService: CompanyService) {

    @PermitAuthority(Authority.GM)
    @GetMapping
    fun findAll(): ResponseEntity<Any?> {
        return ResponseEntity.ok(ResultUtil.success(companyService.findAll()))
    }

    @PermitAuthority(Authority.GM)
    @PostMapping
    fun register(@Valid @RequestBody request: CompanyRegRequest): ResponseEntity<Any?> {
        companyService.register(request)
        return ResponseEntity.ok(ResultUtil.success())
    }

}