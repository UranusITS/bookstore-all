package team.solar.bookstorebackend.service

import team.solar.bookstorebackend.entity.Order

interface OrderService {
    fun getOrderById(id: Int): Order

    fun getOrdersByUserID(user_id: Int): List<Order>

    fun addOrder(order: Order): Int?
}
