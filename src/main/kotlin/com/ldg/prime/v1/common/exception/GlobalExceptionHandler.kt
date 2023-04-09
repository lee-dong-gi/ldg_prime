package com.ldg.prime.v1.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        // 예외 처리 로직
        val errorResponse = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
        return ResponseEntity(errorResponse, errorResponse.status)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        // 예외 처리 로직
        val bindingResult = ex.bindingResult
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST, bindingResult.allErrors[0].defaultMessage)
        return ResponseEntity(errorResponse, errorResponse.status)
    }
}

data class ErrorResponse(val status: HttpStatus, val message: String?)