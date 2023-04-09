package com.ldg.prime.v1.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntityDateTime (regDate: LocalDateTime, modDate: LocalDateTime?){
    @Column(name = "REG_DATE", insertable = false, updatable = false,
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @CreationTimestamp
    var regDate: LocalDateTime? = null
        protected set

    @Column(name = "MOD_DATE", nullable = true, columnDefinition = "DATETIME")
    @UpdateTimestamp
    var modDate: LocalDateTime? = null
        protected set
}

