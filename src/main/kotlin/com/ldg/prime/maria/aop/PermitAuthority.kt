package com.ldg.prime.maria.aop

import com.ldg.prime.maria.common.Authority
import kotlin.annotation.Target
import kotlin.annotation.Retention


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PermitAuthority(val authority: Authority)
