package team.solar.bookstorebackend.daoimpl

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.CartItemDao
import team.solar.bookstorebackend.entity.Book
import team.solar.bookstorebackend.entity.CartItem
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.repository.CartItemRepository

@Repository
class CartItemDaoImpl(val repo: CartItemRepository) : CartItemDao {
    @Cacheable("cartItem-id")
    override fun getCartItemById(id: Int) = repo.getCartItemById(id)

    @Cacheable("cartItem-user", key = "#user.id")
    override fun getItemsByUser(user: User) = repo.getCartItemsByUser(user)

    override fun countCartItemByUserAndBook(user: User?, book: Book?) =
        repo.countCartItemByUserAndBook(user, book)

    @Caching(
        evict = [CacheEvict("cartItem-id", allEntries = true), CacheEvict("cartItem-user", key = "#user.id")]
    )
    override fun deleteByUser(user: User) = repo.deleteByUser(user)

    @Caching(
        put = [CachePut("cartItem-id", key = "#cart_item.id")],
        evict = [CacheEvict("cartItem-user", key = "#cart_item.user.id")]
    )
    override fun save(cart_item: CartItem) = repo.save(cart_item)
}
