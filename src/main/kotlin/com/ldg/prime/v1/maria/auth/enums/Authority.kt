package com.ldg.prime.v1.maria.auth.enums

enum class Authority{
    DM, // development manager
    GM, // general manager
    CM, // company manager
    CU; // company user

    fun checkAboveAuthority(userAuthority: Authority, stdAuthority: Authority): Boolean {
        return when (stdAuthority) {
            DM -> arrayOf(DM).contains(userAuthority)
            GM -> arrayOf(DM, GM).contains(userAuthority)
            CM -> arrayOf(DM, GM, CM).contains(userAuthority)
            CU -> arrayOf(DM, GM, CM, CU).contains(userAuthority)
        }
    }
}
