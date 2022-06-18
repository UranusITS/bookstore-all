package team.solar.bookstorebackend.service

import team.solar.bookstorebackend.entity.OrderItem

interface OrderItemService {
    fun getItemsByOrderID(order_id: Int): List<OrderItem>

    fun addItems(order_items: List<OrderItem>)
}
