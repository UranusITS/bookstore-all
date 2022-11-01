package team.solar.bookstorebackend.daoimpl

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.OrderDao
import team.solar.bookstorebackend.entity.Order
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.repository.OrderRepository
import javax.transaction.Transactional

@Repository
class OrderDaoImpl(val repo: OrderRepository) : OrderDao {
    @Cacheable("order-id")
    override fun getOrderById(id: Int): Order = repo.getOrderById(id)

    @Cacheable("order-user", key = "#user.id")
    override fun getOrdersByUser(user: User) = repo.getOrdersByUser(user)

    @Cacheable("order-all")
    override fun getAllOrders(): List<Order> = repo.findAll().toList()

    @Transactional(Transactional.TxType.REQUIRED)
    @Caching(
        put = [CachePut("order-id", key = "#order.id")],
        evict = [CacheEvict("order-user", key = "#order.user.id"), CacheEvict("order-all", allEntries = true)]
    )
    override fun save(order: Order): Order = repo.save(order)
}
