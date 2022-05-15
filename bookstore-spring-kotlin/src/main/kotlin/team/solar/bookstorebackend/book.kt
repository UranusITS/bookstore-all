package team.solar.bookstorebackend

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@Table("books")
data class Book(
    @Id val id: Int?,
    val isbn: String,
    val name: String,
    val type: String,
    val author: String,
    val price: Double,
    val description: String,
    val inventory: Int,
    val img_path: String
)

interface BookRepository : CrudRepository<Book, Int> {
    @Query("select * from books")
    fun getBooks(): List<Book>

    @Query("select distinct type from books")
    fun getTypes(): List<String>

    @Query("select * from books where type = :type")
    fun getBooksByType(@Param("type") type: String): List<Book>

    @Query(
        "select * from books where locate(:text, type) > 0 or locate(:text, name) > 0 " +
                "or locate(:text, author) > 0 or locate(:text, description) > 0"
    )
    fun getBooksByText(@Param("text") text: String): List<Book>

    @Query("select * from books where id = :id")
    fun getBookById(@Param("id") id: Int): Book?

    @Modifying
    @Query("update books set inventory = inventory + :num where id = :id")
    fun increaseInventoryById(@Param("id") id: Int, @Param("num") num: Int)

    @Modifying
    @Query("update books set inventory = inventory - :num where id = :id")
    fun decreaseInventoryById(@Param("id") id: Int, @Param("num") num: Int)

    @Modifying
    @Query(
        "update books set isbn = :isbn, name = :name, type = :type, author = :author, price = :price, " +
                "description = :description, inventory = :inventory, imgPath = :imgPath where id = :id"
    )
    fun updateAllById(
        @Param("id") id: Int, @Param("isbn") isbn: String, @Param("name") name: String,
        @Param("type") type: String, @Param("author") author: String, @Param("price") price: Double,
        @Param("description") description: String, @Param("inventory") inventory: Int,
        @Param("img_path") img_path: String
    )
}

@Service
class BookService(val db: BookRepository) {
    fun getBooks(): List<Book> = db.getBooks()

    fun getTypes(): List<String> = db.getTypes()

    fun getBooksByType(type: String) = db.getBooksByType(type)

    fun getBooksByTypes(types: List<String>): List<Book> {
        val re = mutableListOf<Book>()
        for (type in types) {
            re.addAll(db.getBooksByType(type))
        }
        return re.toList()
    }

    fun getBooksByText(text: String) = db.getBooksByText(text)

    fun getBookById(id: Int) = db.getBookById(id)

    fun increaseInventory(id: Int, num: Int) = db.increaseInventoryById(id, num)

    fun decreaseInventory(id: Int, num: Int) = db.decreaseInventoryById(id, num)

    fun updateBook(book: Book) {
        book.id?.let {
            db.updateAllById(
                it, book.isbn, book.name, book.type, book.author, book.price,
                book.description, book.inventory, book.img_path
            )
        }
    }

    fun addBook(book: Book) = db.save(book)

    fun deleteBook(id: Int) = db.deleteById(id)
}

@RestController
@RequestMapping("/book")
class BookResource(val service: BookService) {
    @RequestMapping("/books")
    fun getBooks() = service.getBooks()

    @RequestMapping("/types")
    fun getTypes() = service.getTypes()

    @RequestMapping("/get-books-by-type")
    fun getBooksByType(@RequestParam("type") type: String) = service.getBooksByType(type)

    @RequestMapping("/get-books-by-types")
    fun getBooksByTypes(@RequestBody types: List<String>) = service.getBooksByTypes(types)

    @RequestMapping("/get-books-by-text")
    fun getBooksByText(@RequestParam("text") text: String) = service.getBooksByText(text)

    @RequestMapping("/get-book-by-id")
    fun getBookById(@RequestParam("id") id: Int) = service.getBookById(id)

    @RequestMapping("/increase-inventory")
    fun increaseInventory(@RequestParam("id") id: Int, @RequestParam("num") num: Int) =
        service.increaseInventory(id, num)

    @RequestMapping("/decrease-inventory")
    fun decreaseInventory(@RequestParam("id") id: Int, @RequestParam("num") num: Int) =
        service.decreaseInventory(id, num)

    @RequestMapping("/update-book")
    fun updateBook(@RequestBody book: Book) = service.updateBook(book)

    @RequestMapping("/add-book")
    fun addBook(@RequestBody book: Book) = service.addBook(book)

    @RequestMapping("/delete-book")
    fun deleteBook(@RequestParam("id") id: Int) = service.deleteBook(id)
}
