package com.ldg.prime.maria.slave.entity

import jakarta.persistence.*
import lombok.NoArgsConstructor

@NoArgsConstructor
@Entity
@Table(name = "user")
data class R_User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val name: String,
    val email: String? = null
){
    constructor() : this(0, "", "")
}
