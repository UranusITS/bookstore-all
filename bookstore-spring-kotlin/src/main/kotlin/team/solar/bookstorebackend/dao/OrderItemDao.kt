package team.solar.bookstorebackend.dao

import team.solar.bookstorebackend.entity.Order
import team.solar.bookstorebackend.entity.OrderItem

interface OrderItemDao {
    fun getItemsByOrder(order: Order): List<OrderItem>?

    fun saveAll(order_items: List<OrderItem>): List<OrderItem>
}
