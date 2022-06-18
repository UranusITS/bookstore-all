package team.solar.bookstorebackend.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import team.solar.bookstorebackend.entity.OrderItem

interface OrderItemRepository : CrudRepository<OrderItem, Int> {
    @Query("select * from order_items where order_id = :order_id")
    fun getItemsByOrderID(@Param("order_id") order_id: Int): List<OrderItem>
}
