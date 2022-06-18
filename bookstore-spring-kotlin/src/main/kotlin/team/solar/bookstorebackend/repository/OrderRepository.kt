package team.solar.bookstorebackend.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import team.solar.bookstorebackend.entity.Order

interface OrderRepository : CrudRepository<Order, Int> {
    fun getOrderById(@Param("id") id: Int): Order

    @Query("select * from orders where user_id = :user_id")
    fun getOrdersByUserID(@Param("user_id") user_id: Int): List<Order>
}
