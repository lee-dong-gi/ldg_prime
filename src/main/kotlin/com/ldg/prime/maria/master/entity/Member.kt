package com.ldg.prime.maria.master.entity

import com.ldg.prime.maria.dto.request.member.MemberDto
import jakarta.persistence.*

@Entity
@Table(name = "MEMBRE_TBL")
class Member (
        username: String,
        password: String,
) : Timestamped() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, unique = true)
    var username: String = username
        protected set // setter 접근금지 (비즈니스 로직을 반영 => updateUsername)

    @Column(nullable = false)
    var password: String = password
        protected set

    @OneToMany(mappedBy = "member")
    val boards: MutableList<Board> = ArrayList()

    fun updateMember(memberDto: MemberDto) {
        this.username = memberDto.username
        this.password = memberDto.password
    }

    fun updateUsername(username: String) {
        this.username = username
    }

    fun updatePassword(password: String) {
        this.password = password
    }
}