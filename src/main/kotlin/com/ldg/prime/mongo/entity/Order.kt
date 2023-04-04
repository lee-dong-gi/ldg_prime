package com.ldg.prime.mongo.entity

import com.querydsl.core.annotations.QueryEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
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
    @OneToMany
    var items: List<MenuItem> = mutableListOf()

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
}