package team.solar.bookstorebackend.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import team.solar.bookstorebackend.entity.Order
import team.solar.bookstorebackend.service.OrderService
import team.solar.bookstorebackend.websocket.WebSocketServer

@Component
class OrderListener(
    val service: OrderService,
    val kafkaTemplate: KafkaTemplate<String, String>,
    val server: WebSocketServer
) {
    @KafkaListener(topics = ["add_order"], groupId = "add_order_consumers")
    fun addItemListener(record: ConsumerRecord<String, String>) {
        val message: String = record.value()
        val mapper = jacksonObjectMapper()
        val order = mapper.readValue<Order>(message)
        try {
            service.addOrder(order)
        } catch (e: Exception) {
            kafkaTemplate.send("add_order_over", "key", "FAILED${order.user?.username}")
            return
        }
        kafkaTemplate.send("add_order_over", "key", "SUCCESS${order.user?.username}")
    }

    @KafkaListener(topics = ["add_order_over"], groupId = "add_order_consumers")
    fun addItemOverListener(record: ConsumerRecord<String, String>) {
        val res = record.value()
        if (res.substring(0, 6) == "SUCCESS") {
            server.sendMessageToUser(res.substring(6, res.length), "SUCCESS")
        }
        else if (res.substring(0, 6) == "FAILED") {
            server.sendMessageToUser(res.substring(6, res.length), "FAILED")
        }
    }
}