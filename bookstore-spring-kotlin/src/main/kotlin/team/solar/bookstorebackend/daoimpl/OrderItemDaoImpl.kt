package team.solar.bookstorebackend.daoimpl

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.OrderItemDao
import team.solar.bookstorebackend.entity.Order
import team.solar.bookstorebackend.entity.OrderItem
import team.solar.bookstorebackend.repository.OrderItemRepository
import javax.transaction.Transactional

@Repository
class OrderItemDaoImpl(val repo: OrderItemRepository) : OrderItemDao {
    @Cacheable("orderItem-id", key = "#order.id")
    override fun getItemsByOrder(order: Order): List<OrderItem>? = repo.getOrderItemsByOrder(order)

    @Transactional(Transactional.TxType.REQUIRED)
    @CacheEvict("orderItem-id", allEntries = true)
    override fun saveAll(order_items: List<OrderItem>): List<OrderItem> = repo.saveAll(order_items).toList()
}
