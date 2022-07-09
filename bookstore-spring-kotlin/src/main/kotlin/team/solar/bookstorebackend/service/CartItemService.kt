package team.solar.bookstorebackend.service

import team.solar.bookstorebackend.entity.CartItem
import team.solar.bookstorebackend.entity.User

interface CartItemService {
    fun getItemsByUserId(user_id: Int): List<CartItem>?

    fun updateNumById(id: Int, num: Int)

    fun addItem(cart_item: CartItem): Boolean

    fun deleteItemByUserId(user_id: Int)

    fun updateCheckedById(id: Int, checked: Boolean)
}
