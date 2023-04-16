package com.ldg.prime.v1.redis

import com.ldg.prime.v1.common.dto.ResultUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/redis")
class RedisController (val redisService: RedisService){
    @GetMapping("/get")
    fun findAll(@Valid redisGetRequest: RedisGetRequest):ResponseEntity<Any>{
        return ResponseEntity.ok(ResultUtil.success(redisService.getValue(redisGetRequest.key)))
    }

    @PostMapping("/set")
    fun signIn(@RequestBody @Valid redisSetRequest: RedisSetRequest):ResponseEntity<Any?>{
        return ResponseEntity.ok(ResultUtil.success(redisService.setValue(redisSetRequest.key, redisSetRequest.value)))
    }

}