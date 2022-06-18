package team.solar.bookstorebackend.serviceimpl

import org.springframework.stereotype.Service
import team.solar.bookstorebackend.dao.OrderItemDao
import team.solar.bookstorebackend.entity.OrderItem
import team.solar.bookstorebackend.service.OrderItemService

@Service
class OrderItemServiceImpl(val dao: OrderItemDao) : OrderItemService {
    override fun getItemsByOrderID(order_id: Int) = dao.getItemsByOrderID(order_id)

    override fun addItems(order_items: List<OrderItem>) {
        dao.saveAll(order_items)
    }
}
