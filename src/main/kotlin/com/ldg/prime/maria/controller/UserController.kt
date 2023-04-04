package com.ldg.prime.maria.controller

import com.ldg.prime.maria.master.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.support.TransactionSynchronizationManager
import org.springframework.web.bind.annotation.*

@RestController
class UserController {
    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/unified/user")
    fun unifiedGetAll():ResponseEntity<Any?>{
        log.error("{} controller get isCurrentTransactionReadOnly", TransactionSynchronizationManager.isCurrentTransactionReadOnly().toString())
        return ResponseEntity<Any?>(userService.findAll(), HttpStatus.OK)
    }

    @PostMapping("/unified/user")
    fun unifiedSave(){
        log.error("{} controller save isCurrentTransactionReadOnly", TransactionSynchronizationManager.isCurrentTransactionReadOnly().toString())
        userService.save()
    }



}