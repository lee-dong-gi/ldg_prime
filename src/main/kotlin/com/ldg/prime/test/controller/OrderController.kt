package com.ldg.prime.test.controller

import com.ldg.prime.test.entity.MenuItem
import com.ldg.prime.test.repository.OrderRepository
import com.ldg.prime.test.service.OrderService
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