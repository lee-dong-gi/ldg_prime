package com.ldg.prime.v1.mongo.order.controller

import com.ldg.prime.v1.maria.auth.enums.Authority
import com.ldg.prime.v1.common.aop.PermitAuthority
import com.ldg.prime.v1.common.dto.ResultUtil
import com.ldg.prime.v1.mongo.entity.MenuItem
import com.ldg.prime.v1.mongo.order.repository.OrderRepository
import com.ldg.prime.v1.mongo.order.service.OrderService
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
        return ResponseEntity.ok(ResultUtil.success())
    }

    @PermitAuthority(Authority.CU)
    @GetMapping("/store/name/orders")
    fun getAllOrder():ResponseEntity<Any?>{
        return ResponseEntity.ok(ResultUtil.success(orderRepository.findAll()))
    }

    @DeleteMapping("/store/name/orders/all")
    fun deleteAll(): ResponseEntity<Any?>{
        orderRepository.deleteAll()
        return ResponseEntity.ok(ResultUtil.success())
    }
}