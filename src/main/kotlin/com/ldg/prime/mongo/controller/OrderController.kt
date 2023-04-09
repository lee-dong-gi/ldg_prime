package com.ldg.prime.mongo.controller

import com.ldg.prime.maria.aop.PermitAuthority
import com.ldg.prime.maria.common.Authority
import com.ldg.prime.mongo.entity.MenuItem
import com.ldg.prime.mongo.repository.OrderRepository
import com.ldg.prime.mongo.service.OrderService
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class OrderController(
        var orderService: OrderService,
        var orderRepository: OrderRepository
) {

    @PostMapping("/store/name/table/{tableId}/order")
    fun orderMenu(@PathVariable tableId : String, @RequestBody item: MenuItem): ResponseEntity<Any?> {
        orderService.orderMenu(tableId,item)
        return ResponseEntity<Any?>("good", HttpStatus.OK)
    }

    @PermitAuthority(Authority.CU)
    @GetMapping("/store/name/orders")
    fun getAllOrder():ResponseEntity<Any?>{
        return ResponseEntity<Any?>(orderRepository.findAll(), HttpStatus.OK)
    }

    @DeleteMapping("/store/name/orders/all")
    fun deleteAll(): ResponseEntity<Any?>{
        orderRepository.deleteAll()
        return ResponseEntity<Any?>("good", HttpStatus.OK)
    }
}