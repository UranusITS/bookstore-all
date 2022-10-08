package team.solar.bookstorebackend.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.solar.bookstorebackend.entity.Order
import team.solar.bookstorebackend.service.OrderService

@RestController
@RequestMapping("/order")
class OrderController(val service: OrderService, val kafkaTemplate: KafkaTemplate<String, String>) {
    @RequestMapping("/order")
    fun getOrderById(@RequestParam("id") id: Int) = service.getOrderById(id)

    @RequestMapping("/orders")
    fun getOrdersByUserId(@RequestParam("user-id") user_id: Int) = service.getOrdersByUserID(user_id)

    @RequestMapping("/all-orders")
    fun getAllOrders() = service.getAllOrders()

    @RequestMapping("/add-order")
    fun addItem(@RequestBody order: Order) {
        val mapper = jacksonObjectMapper()
        val message = mapper.writeValueAsString(order)
        kafkaTemplate.send("add_order", "key", message)
    }
}
