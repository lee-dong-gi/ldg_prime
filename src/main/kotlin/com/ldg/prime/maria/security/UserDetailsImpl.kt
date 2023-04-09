package com.ldg.prime.maria.security

import com.ldg.prime.maria.master.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(private val user: User) : UserDetails {

    private var enabled: Boolean = true

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? = AuthorityUtils.createAuthorityList(user.authority.toString())

    override fun getPassword(): String? = user.userPassword

    override fun getUsername(): String? = user.userId

    override fun isAccountNonExpired(): Boolean = (user.isUse == true)

    override fun isAccountNonLocked(): Boolean = enabled

    override fun isCredentialsNonExpired(): Boolean = enabled

    override fun isEnabled(): Boolean = enabled
}