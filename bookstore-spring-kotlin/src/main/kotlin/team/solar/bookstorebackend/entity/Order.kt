package team.solar.bookstorebackend.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("orders")
data class Order(
    @Id val id: Int?,
    val user_id: Int,
    val address_id: Int
)
