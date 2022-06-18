package team.solar.bookstorebackend.dao

import team.solar.bookstorebackend.entity.OrderItem

interface OrderItemDao {
    fun getItemsByOrderID(order_id: Int): List<OrderItem>

    fun saveAll(order_items: List<OrderItem>): List<OrderItem>
}
