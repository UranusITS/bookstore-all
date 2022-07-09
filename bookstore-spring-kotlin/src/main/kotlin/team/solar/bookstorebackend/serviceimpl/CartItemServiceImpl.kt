package team.solar.bookstorebackend.serviceimpl

import org.springframework.stereotype.Service
import team.solar.bookstorebackend.dao.CartItemDao
import team.solar.bookstorebackend.entity.CartItem
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.service.CartItemService
import javax.transaction.Transactional

@Service
class CartItemServiceImpl(val dao: CartItemDao) : CartItemService {
    override fun getItemsByUserId(user_id: Int) = dao.getItemsByUser(User(id=user_id))

    override fun updateNumById(id: Int, num: Int) {
        val cartItem = dao.getCartItemById(id)
        if (cartItem != null) {
            cartItem.num = num
            dao.save(cartItem)
        }
    }

    override fun addItem(cart_item: CartItem): Boolean {
        return if (dao.countCartItemByUserAndBook(cart_item.user, cart_item.book) == 0) {
            dao.save(cart_item)
            true
        } else {
            false
        }
    }

    @Transactional
    override fun deleteItemByUserId(user_id: Int) = dao.deleteByUser(User(id=user_id))

    override fun updateCheckedById(id: Int, checked: Boolean) {
        val cartItem = dao.getCartItemById(id)
        if (cartItem != null) {
            cartItem.checked = checked
            dao.save(cartItem)
        }
    }
}
