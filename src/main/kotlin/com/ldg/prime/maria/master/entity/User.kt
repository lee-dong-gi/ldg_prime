package com.ldg.prime.maria.master.entity

import jakarta.persistence.*
import lombok.NoArgsConstructor

@NoArgsConstructor
@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val name: String,
    val email: String? = null
){
    constructor() : this(1,"", "")
}
