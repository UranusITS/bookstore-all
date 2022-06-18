package team.solar.bookstorebackend.controller

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.solar.bookstorebackend.entity.CartItem
import team.solar.bookstorebackend.service.CartItemService

@RestController
@RequestMapping("/cart-item")
class CartItemController(val service: CartItemService) {
    @RequestMapping("/items")
    fun getItemsByUserId(@RequestParam("user-id") user_id: Int) = service.getItemsByUserID(user_id)

    @RequestMapping("/update-num")
    fun increaseNumById(@RequestParam("id") id: Int, @RequestParam("num") num: Int) = service.updateNumById(id, num)

    @RequestMapping("/add-item")
    fun addItem(@RequestBody cart_item: CartItem) = service.addItem(cart_item)

    @RequestMapping("/delete-item")
    fun deleteItem(@RequestParam("user-id") user_id: Int) = service.deleteItemByUserId(user_id)

    @RequestMapping("/update-checked")
    fun updateCheckedById(@RequestParam("id") id: Int, @RequestParam("checked") checked: Boolean) =
        service.updateCheckedById(id, checked)
}
