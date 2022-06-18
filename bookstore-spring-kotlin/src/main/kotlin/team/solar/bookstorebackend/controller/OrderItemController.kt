package team.solar.bookstorebackend.controller

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.solar.bookstorebackend.entity.OrderItem
import team.solar.bookstorebackend.service.OrderItemService

@RestController
@RequestMapping("/order-item")
class OrderItemController(val service: OrderItemService) {
    @RequestMapping("/items")
    fun getItemsByOrderId(@RequestParam("order-id") order_id: Int) = service.getItemsByOrderID(order_id)

    @RequestMapping("/add-items")
    fun addItems(@RequestBody order_items: List<OrderItem>) = service.addItems(order_items)
}
