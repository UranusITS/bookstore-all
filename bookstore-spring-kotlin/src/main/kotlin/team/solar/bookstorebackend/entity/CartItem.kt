package team.solar.bookstorebackend.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("cart_items")
data class CartItem(
    @Id val id: Int?,
    val user_id: Int,
    val book_id: Int,
    val num: Int,
    val checked: Boolean
)
