package com.ldg.prime.v1.redis

import jakarta.validation.constraints.NotBlank

data class RedisGetRequest(
    @field:NotBlank val key: String
)
