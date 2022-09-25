package team.solar.bookstorebackend.serviceimpl

import org.springframework.stereotype.Service
import team.solar.bookstorebackend.dao.BookDao
import team.solar.bookstorebackend.dao.OrderDao
import team.solar.bookstorebackend.dao.OrderItemDao
import team.solar.bookstorebackend.entity.Order
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.service.OrderService
import javax.transaction.Transactional

@Service
class OrderServiceImpl(val dao: OrderDao, val itemsDao: OrderItemDao, val bookDao: BookDao) : OrderService {
    override fun getOrderById(id: Int) = dao.getOrderById(id)

    override fun getOrdersByUserID(user_id: Int) = dao.getOrdersByUser(User(id=user_id))

    override fun getAllOrders() = dao.getAllOrders()

    @Transactional(Transactional.TxType.REQUIRED)
    override fun addOrder(order: Order): Int? {
        val savedOrder = dao.save(order)
        val orderItems = order.orderItems
        if (orderItems != null) {
            for (orderItem in orderItems) {
                orderItem.order = savedOrder
                val books = bookDao.getBooksByName(orderItem.name!!)
                if (books.isNotEmpty()) {
                    val book = books[0]
                    book.inventory = book.inventory?.minus(orderItem.num!!)
                    bookDao.save(book)
                }
            }
            itemsDao.saveAll(orderItems)
        }
        return savedOrder.id
    }
}
