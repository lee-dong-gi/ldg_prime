package com.ldg.prime.v1.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService(val redisTemplate: RedisTemplate<String, Any>) {
    fun setValue(key: String, value: Any) {
        redisTemplate.opsForValue().set(key, value)
    }

    fun getValue(key: String): Any? {
        return redisTemplate.opsForValue().get(key)
    }
}