package team.solar.bookstorebackend.daoimpl

import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.OrderItemDao
import team.solar.bookstorebackend.entity.Order
import team.solar.bookstorebackend.entity.OrderItem
import team.solar.bookstorebackend.repository.OrderItemRepository

@Repository
class OrderItemDaoImpl(val repo: OrderItemRepository) : OrderItemDao {
    override fun getItemsByOrder(order: Order): List<OrderItem>? = repo.getOrderItemsByOrder(order)

    override fun saveAll(order_items: List<OrderItem>): List<OrderItem> = repo.saveAll(order_items).toList()
}
