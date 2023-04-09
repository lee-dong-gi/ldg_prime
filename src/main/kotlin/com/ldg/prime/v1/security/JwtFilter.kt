package com.ldg.prime.v1.security

import com.ldg.prime.v1.common.GlobalConstant.AUTH_HEADER
import com.ldg.prime.v1.common.GlobalConstant.TOKEN_TYPE
import com.ldg.prime.v1.common.utils.JwtUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtUtils: JwtUtils
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authorizationHeader: String =
            request.getHeader(AUTH_HEADER) ?: return filterChain.doFilter(request, response)
        val token = authorizationHeader.substring(TOKEN_TYPE.length)

        if (jwtUtils.validation(token)) {
            val userId = jwtUtils.parseUserId(token)
            val authentication: Authentication = jwtUtils.getAuthentication(userId)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}