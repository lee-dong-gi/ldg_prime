package com.ldg.prime.v1.maria.auth.dto.request

import jakarta.validation.constraints.NotBlank

data class UserSignInRequest(
    @field:NotBlank val userId: String,
    @field:NotBlank val userPassword: String
)
