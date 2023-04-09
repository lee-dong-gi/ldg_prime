package com.ldg.prime.v1.maria.auth.dto.request

import com.ldg.prime.v1.maria.auth.enums.Authority
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.*

data class UserSignUpRequest(
    @field:NotBlank val userId: String,
    @field:NotBlank val userName: String,
    @field:NotBlank val userPassword: String,
    @field:NotBlank val userEmail: String,
    @field:NotBlank val userPhone: String,
    @field:NotBlank val userAddress: String,
    val userAddressDetail: String,
    @field:NotBlank val authority: Authority,
    @field:NotNull val comUuid: UUID
)
