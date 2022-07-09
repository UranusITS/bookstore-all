package team.solar.bookstorebackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.entity.Order
import team.solar.bookstorebackend.entity.OrderItem

@Repository
interface OrderItemRepository : JpaRepository<OrderItem, Int>, JpaSpecificationExecutor<OrderItem> {
    fun getOrderItemsByOrder(order: Order): List<OrderItem>?
}
