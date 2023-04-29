package com.ldg.prime.v1.mongo.entity

import com.querydsl.core.annotations.QueryEntity
import jakarta.persistence.*
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Entity
@QueryEntity
@Document(collection = "orders")
class Order {
    @Id
    var id: String? = null

//    @Field(name="transcationId")
//    var transcationId: String =""

    @Field(name="order_date")
    var orderDate: LocalDateTime = LocalDateTime.now()

    @Field(name="extendedPrice")
    var extendedPrice: Int = 0

    @Field(name="table")
    var table:String = ""

    @Field(name="menus")
    @OneToMany(mappedBy = "order")
    var items: MutableList<MenuItem> = mutableListOf()

    @Field(name="status")
    var status: Int = 0
}

@Entity
@QueryEntity
class MenuItem {
    var category: String =""
    var menuId: String = ""
    @Id var title: String = ""
    var price:Int = 0
    var count:Int = 0
    @ManyToOne
    var order: Order? = null
}