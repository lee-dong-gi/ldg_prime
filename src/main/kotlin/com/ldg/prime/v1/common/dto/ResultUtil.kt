package com.ldg.prime.v1.common.dto

class ResultUtil {
    companion object {
        const val DEFAULT_CODE = "200"
        const val SUCCESS = "success"

        @JvmStatic
        fun success(rows: List<Any>?, page: Int, totalCount: Long, totalPage: Int): ListResult<Any>? {
            val listData: ListData<Any> = ListData(page, totalCount, totalPage, rows)
            return ListResult(SUCCESS, DEFAULT_CODE, listData)
        }

        @JvmStatic
        fun success(rows: List<Any>?): ListNonPageResult<Any> {
            return ListNonPageResult(DEFAULT_CODE, null, SUCCESS, rows)
        }

        @JvmStatic
        fun success(rows: List<Any>?, count: Int?): ListNonPageResult<Any> {
            return ListNonPageResult(DEFAULT_CODE, count, SUCCESS, rows)
        }

        @JvmStatic
        fun success(data: Any?): SingleResult<Any> {
            return SingleResult(DEFAULT_CODE, SUCCESS, data)
        }

        @JvmStatic
        fun success(): Result<Any?> {
            return Result(DEFAULT_CODE, SUCCESS)
        }
    }
}