package com.ldg.prime.maria.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.*

data class CompanyRegRequest(
    @field:NotBlank val comName: String,
    @field:NotNull val comUuid: UUID,
    @field:NotBlank val comPhone: String,
    @field:NotBlank val comEmail: String,
    @field:NotBlank val comAddress: String,
    @field:NotBlank val comAddressDetail: String,
)
