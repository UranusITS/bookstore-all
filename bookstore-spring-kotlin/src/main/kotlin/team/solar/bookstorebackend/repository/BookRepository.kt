package team.solar.bookstorebackend.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import team.solar.bookstorebackend.entity.Book

interface BookRepository : CrudRepository<Book, Int> {
    @Query("select distinct type from books")
    fun getTypes(): List<String>

    fun getBooksByType(type: String): List<Book>

    @Query(
        "select * from books where locate(:text, type) > 0 or locate(:text, name) > 0 " +
                "or locate(:text, author) > 0 or locate(:text, description) > 0"
    )
    fun getBooksByText(@Param("text") text: String): List<Book>

    fun getBookById(id: Int): Book?

    @Modifying
    @Query("update books set inventory = inventory + :num where id = :id")
    fun increaseInventoryById(@Param("id") id: Int, @Param("num") num: Int)

    @Modifying
    @Query("update books set inventory = inventory - :num where id = :id")
    fun decreaseInventoryById(@Param("id") id: Int, @Param("num") num: Int)

    @Modifying
    @Query(
        "update books set isbn = :isbn, name = :name, type = :type, author = :author, price = :price, " +
                "description = :description, inventory = :inventory, img_path = :img_path where id = :id"
    )
    fun updateAllById(
        @Param("id") id: Int, @Param("isbn") isbn: String, @Param("name") name: String,
        @Param("type") type: String, @Param("author") author: String, @Param("price") price: Double,
        @Param("description") description: String, @Param("inventory") inventory: Int,
        @Param("img_path") img_path: String
    )
}
