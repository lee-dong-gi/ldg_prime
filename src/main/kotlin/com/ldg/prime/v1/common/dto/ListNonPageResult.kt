package com.ldg.prime.v1.common.dto
class ListNonPageResult<T> (val result: String?, val count: Int?, val code: String?,
                            val rows: List<T>?
){
}