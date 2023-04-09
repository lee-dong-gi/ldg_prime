package com.ldg.prime.maria.master.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.ldg.prime.maria.common.BaseEntityDateTime
import com.ldg.prime.maria.dto.request.CompanyRegRequest
import com.ldg.prime.maria.dto.request.UserSignUpRequest
import jakarta.persistence.*
import lombok.NoArgsConstructor
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.*

@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "TB_COMPANY")
class Company constructor() : BaseEntityDateTime(LocalDateTime.now(), null) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDX", nullable = true, columnDefinition = "BIGINT(8)")
    var idx: Long? = null
    @Column(name = "COM_NAME", nullable = false, columnDefinition = "VARCHAR(30)")
    var comName: String? = null
    @Column(name = "COM_UUID", nullable = false, columnDefinition = "BINARY(16) DEFAULT UUID()")
    var comUuid: UUID? = null
    @Column(name = "COM_PHONE", nullable = true, columnDefinition = "VARCHAR(30)")
    var comPhone: String? = null
    @Column(name = "COM_EMAIL", nullable = true, columnDefinition = "VARCHAR(50)")
    var comEmail: String? = null
    @Column(name = "COM_ADDRESS", nullable = false, columnDefinition = "VARCHAR(300)")
    var comAddress: String? = null
    @Column(name = "COM_ADDRESS_DETAIL", nullable = true, columnDefinition = "VARCHAR(300)")
    var comAddressDetail: String? = null
    @Column(name = "IS_USE", nullable = false, columnDefinition = "TINYINT(1) DEFAULT TRUE")
    var isUse: Boolean? = null
    @Column(name = "IS_DELETE", nullable = false, columnDefinition = "TINYINT(1) DEFAULT FALSE")
    var isDelete: Boolean? = null

    @JsonBackReference
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    val users: List<User>? = null

    constructor(req: CompanyRegRequest) : this() {
        this.comName = req.comName
        this.comUuid = req.comUuid
        this.comPhone = req.comPhone
        this.comEmail = req.comEmail
        this.comAddress = req.comAddress
        this.comAddressDetail = req.comAddressDetail
        this.isUse = true
        this.isDelete = false
    }
}