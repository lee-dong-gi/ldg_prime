package com.ldg.prime.maria.master.entity

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class Timestamped {

    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()
}