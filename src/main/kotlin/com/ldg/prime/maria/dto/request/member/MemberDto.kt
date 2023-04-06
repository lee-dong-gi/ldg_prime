package com.ldg.prime.maria.dto.request.member

import com.ldg.prime.maria.master.entity.Member

data class MemberDto (
        var username: String,
        var password: String
) {

    fun toEntity(): Member = Member(this.username, this.password)
}
