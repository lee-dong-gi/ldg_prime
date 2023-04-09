package com.ldg.prime.v1.common.utils

import com.ldg.prime.v1.common.GlobalConstant.JWT_SECRET_KEY
import com.ldg.prime.v1.security.UserDetailsServiceImpl
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils(private val userDetailsServiceImpl: UserDetailsServiceImpl) {
    fun createToken(userId: String): String {
        val claims: Claims = Jwts.claims();
        claims["userId"] = userId

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(createExpiredDate())
            .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
            .compact()
    }

    private fun createExpiredDate(): Date? {
        val calendar = Calendar.getInstance()
        //calendar.add(Calendar.HOUR, 8);               // 8시간
        calendar.add(Calendar.DATE, 7) // 1일
        return calendar.time
    }

    fun validation(token: String): Boolean {
        val claims: Claims = getAllClaims(token)
        val exp: Date = claims.expiration
        return exp.after(Date())
    }

    fun parseUserId(token: String): String {
        val claims: Claims = getAllClaims(token)
        return claims["userId"] as String
    }

    fun getAuthentication(userId: String): Authentication {
        val userDetails: UserDetails = userDetailsServiceImpl.loadUserByUsername(userId)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(JWT_SECRET_KEY)
            .parseClaimsJws(token)
            .body
    }
}