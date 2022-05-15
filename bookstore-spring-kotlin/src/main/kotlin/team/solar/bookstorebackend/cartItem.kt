package team.solar.bookstorebackend

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*


@Table("cart_items")
data class CartItem(
    @Id val id: Int?,
    val user_id: Int,
    val book_id: Int,
    val num: Int,
    val checked: Boolean
)

interface CartItemRepository : CrudRepository<CartItem, Int> {
    @Query("select * from cart_items where user_id = :user_id")
    fun getItemsByUserID(@Param("user_id") user_id: Int): List<CartItem>

    @Query("select count(*) from cart_items where user_id = :user_id and book_id = :book_id")
    fun countCartItemByUserIdAnAndBookId(@Param("user_id") user_id: Int, @Param("book_id") book_id: Int): Int

    @Modifying
    @Query("update cart_items set num = :num where id = :id")
    fun updateNumById(@Param("id") id: Int, @Param("num") num: Int)

    @Modifying
    @Query("update cart_items set checked = :checked where id = :id")
    fun updateCheckedById(@Param("id") id: Int, @Param("checked") checked: Boolean)

    @Modifying
    @Query("delete from cart_items where user_id = :user_id and checked = true")
    fun deleteByUserId(@Param("user_id") user_id: Int)
}

@Service
class CartItemService(val db: CartItemRepository) {
    fun getItemsByUserID(user_id: Int) = db.getItemsByUserID(user_id)

    fun updateNumById(id: Int, num: Int) = db.updateNumById(id, num)

    fun addItem(cart_item: CartItem): Boolean {
        return if(db.countCartItemByUserIdAnAndBookId(cart_item.user_id, cart_item.book_id) == 0) {
            db.save(cart_item)
            true
        } else {
            false
        }
    }

    fun deleteItemByUserId(user_id: Int) = db.deleteByUserId(user_id)

    fun updateCheckedById(id: Int, checked: Boolean) = db.updateCheckedById(id, checked)
}

@RestController
@CrossOrigin
@RequestMapping("/cart-item")
class CartItemResource(val service: CartItemService) {
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
