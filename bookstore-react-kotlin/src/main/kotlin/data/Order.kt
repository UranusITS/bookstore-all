package data

import kotlinext.js.js
import kotlinx.coroutines.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import react.Props
import react.State
import web.http.fetch
import web.http.fetchAsync
import web.http.RequestInit


@Serializable
data class Order(
    val id: Int? = null,
    val user: User? = null,
    val address: Address? = null,
    val orderItems: List<OrderItem>? = null
)

data class OrderState(
    var id: Int,
    var address: Address?,
    var orderItems: List<OrderItem>,
    var isModalVisible: Boolean
) : State

data class OrderAdminState(
    var id: Int,
    var user: User?,
    var address: Address?,
    var orderItems: List<OrderItem>,
    var isModalVisible: Boolean
) : State

external interface OrderProps : Props {
    var id: Int
}


data class OrderListState(var orderList: List<Order>) : State

suspend fun getOrderById(id: Int): Order? {
    return Json.decodeFromString(fetchAsync(
        "$backendUrl/order/order?id=$id",
        js { credentials = INCLUDE } as RequestInit
    ).await().text().await())
}

suspend fun addOrder(order: Order) {
    fetch(
        "$backendUrl/order/add-order",
        js {
            credentials = "include"
            method = "post"
            headers = arrayOf(arrayOf("Content-Type", "application/json;charset=UTF-8"))
            body = Json.encodeToString(order)
        } as RequestInit
    )
}

suspend fun getAllOrders(): List<Order> {
    return Json.decodeFromString(fetchAsync(
        "$backendUrl/order/all-orders",
        js { credentials = INCLUDE } as RequestInit
    ).await().text().await())
}

suspend fun getOrdersByUser(user: User): List<Order> {
    return Json.decodeFromString(fetchAsync(
        "$backendUrl/order/orders?user-id=${user.id}",
        js { credentials = INCLUDE } as RequestInit
    ).await().text().await())
}
