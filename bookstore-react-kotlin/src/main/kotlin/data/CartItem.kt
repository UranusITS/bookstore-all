package data

import kotlinx.serialization.Serializable
import react.Props

@Serializable
data class CartItem(val id: Int?, val user_id: Int, val book_id: Int, val num: Int, val checked: Boolean) {
    constructor(props: CartItemProps) : this(props.id, props.userId, props.bookId, props.num, props.checked)
}

external interface CartItemProps : Props {
    var id: Int?
    var userId: Int
    var bookId: Int
    var num: Int
    var checked: Boolean
}
