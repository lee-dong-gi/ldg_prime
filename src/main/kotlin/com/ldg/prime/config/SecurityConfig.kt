package com.ldg.prime.config

import com.ldg.prime.v1.security.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
        private val jwtAuthenticationFilter: JwtFilter
){

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    /**
     *
     */
    @Bean
    @Throws(java.lang.Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf().disable()
        http.authorizeHttpRequests()
                .requestMatchers("/chat**").permitAll()
                .requestMatchers("/topic/**").permitAll()
                .requestMatchers("/ws/**").permitAll()
                .requestMatchers("/static/**").permitAll() // 정적 파일 경로 허용 설정
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/web/v1/**").permitAll()
                .requestMatchers("/store/**").authenticated()
                .requestMatchers("/store/**").hasAnyRole("DM", "CM")
//                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
                .formLogin().disable()
        return http.build()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }
}