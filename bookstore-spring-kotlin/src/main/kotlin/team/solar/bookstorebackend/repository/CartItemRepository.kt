package team.solar.bookstorebackend.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import team.solar.bookstorebackend.entity.CartItem

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
