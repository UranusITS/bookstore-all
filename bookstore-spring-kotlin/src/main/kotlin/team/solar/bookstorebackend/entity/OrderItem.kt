package team.solar.bookstorebackend.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

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
