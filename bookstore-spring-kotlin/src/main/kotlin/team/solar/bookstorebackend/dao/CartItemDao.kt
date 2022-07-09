package team.solar.bookstorebackend.dao

import team.solar.bookstorebackend.entity.Book
import team.solar.bookstorebackend.entity.CartItem
import team.solar.bookstorebackend.entity.User

interface CartItemDao {
    fun getCartItemById(id: Int): CartItem?

    fun getItemsByUser(user: User): List<CartItem>?

    fun countCartItemByUserAndBook(user: User?, book: Book?): Int

    fun deleteByUser(user: User)

    fun save(cart_item: CartItem): CartItem
}
