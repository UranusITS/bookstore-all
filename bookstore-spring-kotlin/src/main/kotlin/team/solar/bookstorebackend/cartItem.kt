package team.solar.bookstorebackend

import org.springframework.data.annotation.Id
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

    @Query("update cart_items set num = :num + :inc_num where id = :id")
    fun increaseNumById(@Param("id") id: Int, @Param("inc_num") inc_num: Int)

    @Query("update cart_items set num = :num - :dec_num where id = :id")
    fun decreaseNumById(@Param("id") id: Int, @Param("dec_num") dec_num: Int)

    @Query("update cart_items set checked = :checked where id = :id")
    fun updateCheckedById(@Param("id") id: Int, @Param("checked") checked: Boolean)
}

@Service
class CartItemService(val db: CartItemRepository) {
    fun getItemsByUserID(user_id: Int) = db.getItemsByUserID(user_id)

    fun increaseNumById(id: Int, num: Int) = db.increaseNumById(id, num)

    fun decreaseNumById(id: Int, num: Int) = db.decreaseNumById(id, num)

    fun addItem(cart_item: CartItem) = db.save(cart_item)

    fun deleteItemById(id: Int) = db.deleteById(id)

    fun updateCheckedById(id: Int, checked: Boolean) = db.updateCheckedById(id, checked)
}

@RestController
@CrossOrigin
@RequestMapping("/cart-item")
class CartItemResource(val service: CartItemService) {
    @RequestMapping("/items")
    fun getItemsByUserId(@RequestParam("user-id") user_id: Int) = service.getItemsByUserID(user_id)

    @RequestMapping("/increase-num")
    fun increaseNumById(@RequestParam("id") id: Int, @RequestParam("num") num: Int) = service.increaseNumById(id, num)

    @RequestMapping("/decrease-num")
    fun decreaseNumById(@RequestParam("id") id: Int, @RequestParam("num") num: Int) = service.decreaseNumById(id, num)

    @RequestMapping("/add-item")
    fun addItem(@RequestBody cart_item: CartItem) = service.addItem(cart_item)

    @RequestMapping("/delete-item")
    fun deleteItem(@RequestParam("id") id: Int) = service.deleteItemById(id)

    @RequestMapping("/update-checked")
    fun updateCheckedById(@RequestParam("id") id: Int, @RequestParam("checked") checked: Boolean) =
        service.updateCheckedById(id, checked)
}
