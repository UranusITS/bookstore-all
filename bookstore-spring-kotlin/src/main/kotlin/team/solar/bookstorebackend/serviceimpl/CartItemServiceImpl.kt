package team.solar.bookstorebackend.serviceimpl

import org.springframework.stereotype.Service
import team.solar.bookstorebackend.dao.CartItemDao
import team.solar.bookstorebackend.entity.CartItem
import team.solar.bookstorebackend.service.CartItemService

@Service
class CartItemServiceImpl(val dao: CartItemDao) : CartItemService {
    override fun getItemsByUserID(user_id: Int) = dao.getItemsByUserID(user_id)

    override fun updateNumById(id: Int, num: Int) = dao.updateNumById(id, num)

    override fun addItem(cart_item: CartItem): Boolean {
        return if (dao.countCartItemByUserIdAnAndBookId(cart_item.user_id, cart_item.book_id) == 0) {
            dao.save(cart_item)
            true
        } else {
            false
        }
    }

    override fun deleteItemByUserId(user_id: Int) = dao.deleteByUserId(user_id)

    override fun updateCheckedById(id: Int, checked: Boolean) = dao.updateCheckedById(id, checked)
}
