package com.ldg.prime.v1.mongo.order.repository

import com.ldg.prime.v1.mongo.entity.Order
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : MongoRepository<Order, String>, QuerydslPredicateExecutor<Order>