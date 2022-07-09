package team.solar.bookstorebackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.entity.Order
import team.solar.bookstorebackend.entity.User

@Repository
interface OrderRepository : JpaRepository<Order, Int>, JpaSpecificationExecutor<Order> {
    fun getOrderById(id: Int): Order

    fun getOrdersByUser(user: User): List<Order>
}
