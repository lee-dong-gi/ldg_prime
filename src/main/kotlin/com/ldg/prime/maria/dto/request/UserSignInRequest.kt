package com.ldg.prime.maria.dto.request

import jakarta.validation.constraints.NotBlank

data class UserSignInRequest(
    @field:NotBlank val userId: String,
    @field:NotBlank val userPassword: String
)
