package com.ldg.prime.test.service

import com.ldg.prime.test.entity.MenuItem
import com.ldg.prime.test.entity.Order
import com.ldg.prime.test.entity.QOrder.order
import com.ldg.prime.test.repository.OrderRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.where
import org.springframework.data.mongodb.core.updateFirst
import org.springframework.stereotype.Service

@Service
class OrderService(
        var orderRepository: OrderRepository,
        val mongoTemplate: MongoTemplate
){
    fun orderMenu(tableId:String, menuItem: MenuItem){
        //현재 주문에 들어간 테이블이 있는지 체크
        val exOrder = orderRepository.findOne(
                order.status.eq(1).and(order.table.eq(tableId))
        )

        //없으면 첫 주문, 추후 list로 주문을 받는 형식으로 수정 예정
        if(exOrder.isEmpty){
            val tableOrder = Order()
            tableOrder.status = 1
            tableOrder.items = mutableListOf(menuItem)
            tableOrder.table = tableId
            tableOrder.extendedPrice = menuItem.price*menuItem.count
            orderRepository.save(tableOrder)
        }
        else
        {
            val tableOrder = exOrder.get()
            var found : Boolean = false
            for(item in tableOrder.items){
                if(item.menuId==menuItem.menuId){
                    //수량은 추가 주문 수량이 들어옴, 수량 수정 및 최종 가격 sum
                    item.count += menuItem.count
                    tableOrder.extendedPrice +=menuItem.price * menuItem.count
                    //저장
                    orderRepository.save(tableOrder)
                    found=true
                }
            }
            if(!found){
                //새 메뉴 추가
                val extendedPrice = tableOrder.extendedPrice + menuItem.price*menuItem.count
                mongoTemplate.updateFirst<Order>(
                        query(
                                where(Order::table).`is`(tableId)
                        ),
                        Update()
                                .set("extendedPrice",extendedPrice)
                                .addToSet("items",menuItem)
                ).modifiedCount
            }
        }
    }
}