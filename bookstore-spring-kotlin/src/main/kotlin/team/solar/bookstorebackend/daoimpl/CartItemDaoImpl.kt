package team.solar.bookstorebackend.daoimpl

import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.CartItemDao
import team.solar.bookstorebackend.entity.CartItem
import team.solar.bookstorebackend.repository.CartItemRepository

@Repository
class CartItemDaoImpl(val repo: CartItemRepository) : CartItemDao {
    override fun getItemsByUserID(user_id: Int): List<CartItem> = repo.getItemsByUserID(user_id)

    override fun countCartItemByUserIdAnAndBookId(user_id: Int, book_id: Int): Int =
        repo.countCartItemByUserIdAnAndBookId(user_id, book_id)

    override fun updateNumById(id: Int, num: Int) = repo.updateNumById(id, num)

    override fun updateCheckedById(id: Int, checked: Boolean) = repo.updateCheckedById(id, checked)

    override fun deleteByUserId(user_id: Int) = repo.deleteById(user_id)

    override fun save(cart_item: CartItem) = repo.save(cart_item)
}
