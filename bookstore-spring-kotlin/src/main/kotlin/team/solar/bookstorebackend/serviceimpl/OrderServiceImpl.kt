package team.solar.bookstorebackend.serviceimpl

import org.springframework.stereotype.Service
import team.solar.bookstorebackend.dao.OrderDao
import team.solar.bookstorebackend.entity.Order
import team.solar.bookstorebackend.service.OrderService

@Service
class OrderServiceImpl(val dao: OrderDao) : OrderService {
    override fun getOrderById(id: Int) = dao.getOrderById(id)

    override fun getOrdersByUserID(user_id: Int) = dao.getOrdersByUserID(user_id)

    override fun addOrder(order: Order): Int? {
        return dao.save(order).id
    }
}
