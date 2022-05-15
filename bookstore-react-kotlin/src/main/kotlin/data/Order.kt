package data

import kotlinx.serialization.Serializable


@Serializable
data class Order(
    val id: Int?,
    val user_id: Int,
    val address_id: Int
)
