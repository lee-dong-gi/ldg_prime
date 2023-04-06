package com.ldg.prime.maria.utils

import com.ldg.prime.maria.security.UserDetailsServiceImpl

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

    val EXP_TIME: Long = 1000L * 60 * 3
    val JWT_SECRET: String = "secret"
    val SIGNATURE_ALG: SignatureAlgorithm = SignatureAlgorithm.HS256

    fun createToken(username: String): String {
        val claims: Claims = Jwts.claims();
        claims["username"] = username

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date(System.currentTimeMillis()+ EXP_TIME))
                .signWith(SIGNATURE_ALG, JWT_SECRET)
                .compact()
    }

    fun validation(token: String) : Boolean {
        val claims: Claims = getAllClaims(token)
        val exp: Date = claims.expiration
        return exp.after(Date())
    }

    fun parseUsername(token: String): String {
        val claims: Claims = getAllClaims(token)
        return claims["username"] as String
    }

    fun getAuthentication(username: String): Authentication {
        val userDetails: UserDetails = userDetailsServiceImpl.loadUserByUsername(username)

        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .body
    }
}