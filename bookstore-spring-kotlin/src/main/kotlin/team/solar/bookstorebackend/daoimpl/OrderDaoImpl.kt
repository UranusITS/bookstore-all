package team.solar.bookstorebackend.daoimpl

import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.OrderDao
import team.solar.bookstorebackend.entity.Order
import team.solar.bookstorebackend.repository.OrderRepository

@Repository
class OrderDaoImpl(val repo: OrderRepository) : OrderDao {
    override fun getOrderById(id: Int): Order = repo.getOrderById(id)

    override fun getOrdersByUserID(user_id: Int): List<Order> = repo.getOrdersByUserID(user_id)

    override fun save(order: Order): Order = repo.save(order)
}
