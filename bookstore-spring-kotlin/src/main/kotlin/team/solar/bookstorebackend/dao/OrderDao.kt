package team.solar.bookstorebackend.dao

import team.solar.bookstorebackend.entity.Order
import team.solar.bookstorebackend.entity.User

interface OrderDao {
    fun getOrderById(id: Int): Order

    fun getOrdersByUser(user: User): List<Order>

    fun getAllOrders(): List<Order>

    fun save(order: Order): Order
}
