package com.ldg.prime.v1.common.dto

class ListResult<T> (val result: String?, val code: String?, val data: ListData<T>?){
}