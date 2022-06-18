package team.solar.bookstorebackend.dao

import team.solar.bookstorebackend.entity.CartItem

interface CartItemDao {
    fun getItemsByUserID(user_id: Int): List<CartItem>

    fun countCartItemByUserIdAnAndBookId(user_id: Int, book_id: Int): Int

    fun updateNumById(id: Int, num: Int)

    fun updateCheckedById(id: Int, checked: Boolean)

    fun deleteByUserId(user_id: Int)

    fun save(cart_item: CartItem): CartItem
}
