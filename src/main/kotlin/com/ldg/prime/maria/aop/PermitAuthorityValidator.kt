package com.ldg.prime.maria.aop

import com.ldg.prime.maria.common.Authority
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

class PermitAuthorityValidator: HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler !is HandlerMethod) {
            return true
        }

        val annotationAuthority: PermitAuthority = handler.getMethodAnnotation(PermitAuthority::class.java) ?: return true
        val authority: Authority = annotationAuthority.authority
        SecurityContextHolder.getContext().authentication.authorities.forEach { target ->
            val isPass : Boolean = authority.checkAboveAuthority(Authority.valueOf(target.authority), authority)
            if (isPass) {
                return true
            }
        }

        return false // TODO throw
    }

}