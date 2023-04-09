package com.ldg.prime.v1.common.aop

import com.ldg.prime.v1.maria.auth.enums.Authority


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PermitAuthority(val authority: Authority)
