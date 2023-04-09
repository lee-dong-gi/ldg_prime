package com.ldg.prime.config

import com.ldg.prime.maria.aop.PermitAuthorityValidator
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@RequiredArgsConstructor
class WebMvcConfig : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(permitAuthorityValidator())
            .addPathPatterns("/api/v1/**")
            .addPathPatterns("/store/**")
            .order(1)
    }
    @Bean
    fun permitAuthorityValidator(): PermitAuthorityValidator {
        return PermitAuthorityValidator()
    }
}