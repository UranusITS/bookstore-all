package team.solar.bookstorebackend.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("address")
data class Address(
    @Id val id: Int?,
    val user_id: Int,
    val name: String,
    val phone_number: String,
    val address_detail: String
)
