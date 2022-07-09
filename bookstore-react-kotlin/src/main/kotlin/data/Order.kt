package data

import kotlinx.browser.window
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
): State

data class OrderAdminState(
    var id: Int,
    var user: User?,
    var address: Address?,
    var orderItems: List<OrderItem>,
    var isModalVisible: Boolean
): State

external interface OrderProps : Props {
    var id: Int
}


data class OrderListState(var orderList: List<Order>): State

suspend fun getOrderById(id: Int): Order? {
    val response = window.fetch("$backendUrl/order/order?id=$id")
        .await()
        .text()
        .await()
    return Json.decodeFromString(response)
}

suspend fun addOrder(order: Order): Int {
    val headers = Headers()
    headers.append("Content-Type", "application/json;charset=UTF-8")
    return window.fetch(
        "$backendUrl/order/add-order",
        RequestInit(method = "POST", headers = headers, body = Json.encodeToString(order))
    )
        .await()
        .text()
        .await()
        .toInt()
}

suspend fun getAllOrders(): List<Order> {
    val response = window.fetch("$backendUrl/order/all-orders")
        .await()
        .text()
        .await()
    return Json.decodeFromString(response)
}

suspend fun getOrdersByUser(user: User): List<Order> {
    val response = window.fetch("$backendUrl/order/orders?user-id=${user.id}")
        .await()
        .text()
        .await()
    return Json.decodeFromString(response)
}
