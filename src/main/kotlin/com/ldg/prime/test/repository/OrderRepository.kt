package com.ldg.prime.test.repository

import com.ldg.prime.test.entity.Order
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: MongoRepository<Order, String>, QuerydslPredicateExecutor<Order>