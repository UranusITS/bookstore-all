package team.solar.bookstorebackend.dao

import team.solar.bookstorebackend.entity.Order

interface OrderDao {
    fun getOrderById(id: Int): Order

    fun getOrdersByUserID(user_id: Int): List<Order>

    fun save(order: Order): Order
}
