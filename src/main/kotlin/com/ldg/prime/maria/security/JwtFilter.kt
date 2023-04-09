package com.ldg.prime.maria.security

import com.ldg.prime.maria.common.GlobalConstant.AUTH_HEADER
import com.ldg.prime.maria.common.GlobalConstant.SESSION_USER_INFO
import com.ldg.prime.maria.common.GlobalConstant.TOKEN_TYPE
import com.ldg.prime.maria.master.entity.User
import com.ldg.prime.maria.master.repository.UserRepository
import com.ldg.prime.maria.utils.JwtUtils
import io.jsonwebtoken.Claims
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
    ) {

        val authorizationHeader: String = request.getHeader(AUTH_HEADER) ?: return filterChain.doFilter(request, response)
        val token = authorizationHeader.substring(TOKEN_TYPE.length)

        if (jwtUtils.validation(token)) {
            val userId = jwtUtils.parseUserId(token)
            val authentication: Authentication = jwtUtils.getAuthentication(userId)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun userInfoSettingInSession(request: HttpServletRequest, claims: Claims) {
        val session: HttpSession = request.session
        val sessionAdminInfo: Any = session.getAttribute(SESSION_USER_INFO)
        if (sessionAdminInfo !is User) {
            val user: User = userRepository.findByUserId(claims["userId"].toString())
                .orElseThrow { throw UsernameNotFoundException("존재하지 않는 userId 입니다.") }
            session.setAttribute(SESSION_USER_INFO, user)
            request.setAttribute(SESSION_USER_INFO, user)
        }
    }
}