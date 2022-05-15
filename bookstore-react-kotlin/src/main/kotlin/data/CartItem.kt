package data

import kotlinx.coroutines.Job
import kotlinx.serialization.Serializable
import react.Props
import react.State

@Serializable
data class CartItem(val id: Int?, val user_id: Int, val book_id: Int, var num: Int, val checked: Boolean) {
    constructor(props: CartItemProps) : this(props.id, props.userId, props.bookId, props.num, props.checked)
}

data class CartItemState(var cartItem: CartItem, var book: Book) : State

external interface CartItemProps : Props {
    var id: Int?
    var userId: Int
    var bookId: Int
    var num: Int
    var checked: Boolean
    var onPriceChange: () -> Job
}
