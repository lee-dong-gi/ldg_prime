package com.ldg.prime.maria.controller

import com.ldg.prime.maria.master.entity.User
import com.ldg.prime.maria.slave.repository.SlaveUserRepository
import com.ldg.prime.maria.master.repository.UserRepository
import com.ldg.prime.maria.slave.entity.R_User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
        var userRepository: UserRepository,
        var slaveUserRepository: SlaveUserRepository
) {

    @PostMapping("/master/user")
    fun saveMaster():ResponseEntity<Any?>{
        val user = User(1,"mtest2","mtest22")
        return ResponseEntity<Any?>(userRepository.save(user), HttpStatus.OK)
    }

    @PostMapping("/slave/user")
    fun saveSlave():ResponseEntity<Any?>{
        val user = R_User(2,"stest2","stest22")
        return ResponseEntity<Any?>(slaveUserRepository.save(user), HttpStatus.OK)
    }

    @GetMapping("/master/user")
    fun getAllMaster():ResponseEntity<Any?>{
        return ResponseEntity<Any?>(userRepository.findAll(), HttpStatus.OK)
    }
    @GetMapping("/slave/user")
    fun getAllSlaveUser():ResponseEntity<Any?>{
        return ResponseEntity<Any?>(slaveUserRepository.findAll(), HttpStatus.OK)
    }

}