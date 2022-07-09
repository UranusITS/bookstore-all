package team.solar.bookstorebackend.serviceimpl

import org.springframework.stereotype.Service
import team.solar.bookstorebackend.dao.OrderDao
import team.solar.bookstorebackend.dao.OrderItemDao
import team.solar.bookstorebackend.entity.Order
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.service.OrderService
import javax.transaction.Transactional

@Service
class OrderServiceImpl(val dao: OrderDao, val itemsDao: OrderItemDao) : OrderService {
    override fun getOrderById(id: Int) = dao.getOrderById(id)

    override fun getOrdersByUserID(user_id: Int) = dao.getOrdersByUser(User(id=user_id))

    @Transactional
    override fun addOrder(order: Order): Int? {
        val savedOrder = dao.save(order)
        val orderItems = order.orderItems
        if (orderItems != null) {
            for (orderItem in orderItems) {
                orderItem.order = savedOrder
            }
            itemsDao.saveAll(orderItems)
        }
        return savedOrder.id
    }
}
