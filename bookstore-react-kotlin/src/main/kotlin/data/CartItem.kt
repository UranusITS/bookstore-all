package data

import kotlinx.browser.window
import kotlinx.coroutines.Job
import kotlinx.coroutines.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import react.Props
import react.State

@Serializable
data class CartItem(
    val id: Int? = null,
    val user: User? = null,
    val book: Book? = null,
    var num: Int? = null,
    val checked: Boolean? = null
) {
    constructor(props: CartItemProps) : this(
        props.id,
        User(id = props.userId),
        Book(id = props.bookId),
        props.num,
        props.checked
    )
}

data class CartItemState(var cartItem: CartItem, var book: Book?) : State

external interface CartItemProps : Props {
    var id: Int?
    var userId: Int
    var bookId: Int
    var num: Int
    var checked: Boolean
    var onPriceChange: () -> Job
}

suspend fun addCartItem(user: User, book: Book): Boolean {
    val headers = Headers()
    headers.append("Content-Type", "application/json;charset=UTF-8")
    val cartItem = CartItem(null, user, book, 1, true)
    val response = window.fetch(
        "$backendUrl/cart-item/add-item",
        RequestInit(
            method = "POST",
            headers = headers,
            body = Json.encodeToString(cartItem)
        )
    )
        .await()
        .text()
        .await()
    return response.toBoolean()
}

suspend fun getCartItems(user: User): List<CartItem> {
    val response = window.fetch("$backendUrl/cart-item/items?user-id=${user.id}")
        .await()
        .text()
        .await()
    return Json.decodeFromString(response)
}

suspend fun clearCartItems(user: User) {
    window.fetch("$backendUrl/cart-item/delete-item?user-id=${user.id}")
        .await()
}
