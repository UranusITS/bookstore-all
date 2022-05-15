package data


@kotlinx.serialization.Serializable
data class OrderItem(
    val id: Int?,
    val order_id: Int,
    val name: String,
    val author: String,
    val price: Double,
    val num: Int,
    val img_path: String
)