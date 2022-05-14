package team.solar.bookstorebackend

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*


@Table("orders")
data class Order(
    @Id val id: Int?,
    val user_id: Int,
    val address_id: Int
)

interface OrderRepository : CrudRepository<Order, Int> {
    @Query("select * from orders where id = :id")
    fun getOrderByID(@Param("id") id: Int): Order

    @Query("select * from orders where user_id = :user_id")
    fun getOrdersByUserID(@Param("user_id") user_id: Int): List<Order>
}

@Service
class OrderService(val db: OrderRepository) {
    fun getOrderByID(id: Int) = db.getOrderByID(id)

    fun getOrdersByUserID(user_id: Int) = db.getOrdersByUserID(user_id)

    fun addOrder(order: Order): Int? {
        return db.save(order).id
    }
}

@RestController
@CrossOrigin
@RequestMapping("/order")
class OrderResource(val service: OrderService) {
    @RequestMapping("/order")
    fun getOrderById(@RequestParam("id") id: Int) = service.getOrderByID(id)

    @RequestMapping("/orders")
    fun getOrdersByUserId(@RequestParam("user-id") user_id: Int) = service.getOrdersByUserID(user_id)

    @RequestMapping("/add-order")
    fun addItem(@RequestBody order: Order) = service.addOrder(order)
}
