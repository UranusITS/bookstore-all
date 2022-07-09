package team.solar.bookstorebackend.daoimpl

import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.CartItemDao
import team.solar.bookstorebackend.entity.Book
import team.solar.bookstorebackend.entity.CartItem
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.repository.CartItemRepository

@Repository
class CartItemDaoImpl(val repo: CartItemRepository) : CartItemDao {
    override fun getCartItemById(id: Int) = repo.getCartItemById(id)

    override fun getItemsByUser(user: User) = repo.getCartItemsByUser(user)

    override fun countCartItemByUserAndBook(user: User?, book: Book?) =
        repo.countCartItemByUserAndBook(user, book)

    override fun deleteByUser(user: User) = repo.deleteByUser(user)

    override fun save(cart_item: CartItem) = repo.save(cart_item)
}
