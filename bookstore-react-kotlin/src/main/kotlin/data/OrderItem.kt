package data

import kotlinx.serialization.Serializable
import react.Props


@Serializable
data class OrderItem(
    val id: Int? = null,
    val order_id: Int? = null,
    val name: String? = null,
    val author: String? = null,
    val price: Double? = null,
    val num: Int? = null,
    val img_path: String? = null
)

external interface OrderItemProps : Props {
    var id: Int
    var name: String
    var author: String
    var price: Double
    var num: Int
    var img_path: String
}
