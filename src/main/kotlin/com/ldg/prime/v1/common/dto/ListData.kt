package com.ldg.prime.v1.common.dto

class ListData<T>(val page: Int, val totalCount: Long, val totalPage: Int,
                  val rows: List<Any>?
) {
}