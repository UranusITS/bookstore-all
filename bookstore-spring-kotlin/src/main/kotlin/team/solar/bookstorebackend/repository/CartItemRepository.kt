package team.solar.bookstorebackend.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.entity.Book
import team.solar.bookstorebackend.entity.CartItem
import team.solar.bookstorebackend.entity.User
import javax.transaction.Transactional

@Repository
interface CartItemRepository : JpaRepository<CartItem, Int>, JpaSpecificationExecutor<CartItem> {
    fun getCartItemById(id: Int): CartItem?

    fun getCartItemsByUser(user: User): List<CartItem>?

    fun countCartItemByUserAndBook(user: User?, book: Book?): Int

    @Modifying
    fun deleteByUser(user: User)
}
