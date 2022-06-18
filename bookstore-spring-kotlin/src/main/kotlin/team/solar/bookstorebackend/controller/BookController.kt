package team.solar.bookstorebackend.controller

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.solar.bookstorebackend.entity.Book
import team.solar.bookstorebackend.service.BookService


@RestController
@RequestMapping("/book")
class BookController(val service: BookService) {
    @RequestMapping("/books")
    fun findAll() = service.findAll()

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
