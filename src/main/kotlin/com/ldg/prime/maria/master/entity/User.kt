package com.ldg.prime.maria.master.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.ldg.prime.maria.common.Authority
import com.ldg.prime.maria.common.BaseEntityDateTime
import com.ldg.prime.maria.dto.request.UserSignUpRequest
import jakarta.persistence.*
import lombok.NoArgsConstructor
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime

@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "TB_USER")
class User constructor() : BaseEntityDateTime(LocalDateTime.now(), null) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDX", nullable = true, columnDefinition = "BIGINT(8) AUTO_INCREMENT")
    var idx: Long? = null
    @Column(name = "USER_ID", nullable = true, columnDefinition = "VARCHAR(30)")
    var userId: String? = null
    @Column(name = "USER_PASSWORD", nullable = true, columnDefinition = "VARCHAR(300)")
    var userPassword: String? = null
    @Column(name = "USER_NAME", nullable = true, columnDefinition = "VARCHAR(30)")
    var userName: String? = null
    @Column(name = "USER_EMAIL", nullable = true, columnDefinition = "VARCHAR(50)")
    var userEmail: String? = null
    @Column(name = "USER_PHONE", nullable = true, columnDefinition = "VARCHAR(30)")
    var userPhone: String? = null
    @Column(name = "USER_ADDRESS", nullable = false, columnDefinition = "VARCHAR(300)")
    var userAddress: String? = null
    @Column(name = "USER_ADDRESS_DETAIL", nullable = true, columnDefinition = "VARCHAR(300)")
    var userAddressDetail: String? = null
    @Column(name = "AUTHORITY", nullable = true, columnDefinition = "VARCHAR(30)")
    @Enumerated(EnumType.STRING)
    var authority: Authority? = null
    @Column(name = "IS_DELETE", nullable = false, columnDefinition = "TINYINT(1) DEFAULT TRUE")
    var isDelete: Boolean? = null
    @Column(name = "IS_USE", nullable = false, columnDefinition = "TINYINT(1) DEFAULT TRUE")
    var isUse: Boolean? = null

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_IDX", referencedColumnName = "IDX")
    var company: Company? = null

    constructor(req: UserSignUpRequest, company: Company) : this() {
        this.userId = req.userId
        this.userPassword = req.userPassword
        this.company = company
        this.userEmail = req.userEmail
        this.userName = req.userName
        this.userPhone = req.userPhone
        this.authority = req.authority
        this.userAddress = req.userAddress
        this.userAddressDetail = req.userAddressDetail
        this.isUse = true
        this.isDelete = false
    }
}