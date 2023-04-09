package com.ldg.prime.maria.common

import io.jsonwebtoken.SignatureAlgorithm

object GlobalConstant {
    const val AUTH_HEADER = "Authorization"
    const val SESSION_USER_INFO = "sessionUserInfo"
    const val TOKEN_TYPE = "Bearer "
    const val ENC_PADDING = "AES/CBC/PKCS5Padding"
    const val JWT_SECRET_KEY = "secretkeysecretkeysecretkey"
    const val EXP_TIME: Long = 1000L * 60 * 3
    const val COMMON_DATE_FORMAT = "yyyyMMddHHmmss"
    const val COMMON_DATE_FORMAT_WITHOUT_TIME = "yyyyMMdd"

    const val READ_ONLY_TM = "slaveTransactionManager"
    const val WRITE_ONLY_TM = "masterTransactionManager"
}