package team.solar.bookstorebackend

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*


@Table("order_items")
data class OrderItem(
    @Id val id: Int?,
    val order_id: Int,
    val name: String,
    val author: String,
    val price: Double,
    val num: Int,
    val img_path: String
)

interface OrderItemRepository : CrudRepository<OrderItem, Int> {
    @Query("select * from order_items where order_id = :order_id")
    fun getItemsByOrderID(@Param("order_id") order_id: Int): List<OrderItem>
}

@Service
class OrderItemService(val db: OrderItemRepository) {
    fun getItemsByOrderID(order_id: Int) = db.getItemsByOrderID(order_id)

    fun addItems(order_items: List<OrderItem>) {
        db.saveAll(order_items)
    }
}

@RestController
@CrossOrigin
@RequestMapping("/order-item")
class OrderItemResource(val service: OrderItemService) {
    @RequestMapping("/items")
    fun getItemsByOrderId(@RequestParam("order-id") order_id: Int) = service.getItemsByOrderID(order_id)

    @RequestMapping("/add-items")
    fun addItems(@RequestBody order_items: List<OrderItem>) = service.addItems(order_items)
}
