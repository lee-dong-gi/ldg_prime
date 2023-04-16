package com.ldg.prime.v1.redis

import jakarta.validation.constraints.NotBlank

data class RedisSetRequest(
    @field:NotBlank val key: String,
    @field:NotBlank val value: String
)
