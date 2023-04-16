package com.ldg.prime.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Autowired
    private lateinit var redisProperties: RedisProperties

/*    @Bean
    fun redisClusterConfiguration(): RedisClusterConfiguration {
        val configuration = RedisClusterConfiguration(redisProperties.cluster.nodes)
        configuration.password = RedisPassword.of(redisProperties.password)
        return configuration
    }*/

    @Bean
    fun redisStandAloneConfiguration(): RedisStandaloneConfiguration {
        val configuration = RedisStandaloneConfiguration(redisProperties.host, redisProperties.port)
        configuration.password = RedisPassword.of(redisProperties.password)
        return configuration
    }

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(redisStandAloneConfiguration())
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.setConnectionFactory(redisConnectionFactory())
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        return redisTemplate
    }
}