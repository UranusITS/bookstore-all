package team.solar.bookstorebackend.service

import team.solar.bookstorebackend.entity.Book

interface BookService {
    fun findAll(): List<Book>

    fun getTypes(): List<String>

    fun getBooksByType(type: String): List<Book>

    fun getBooksByTypes(types: List<String>): List<Book>

    fun getBooksByText(text: String): List<Book>

    fun getBookById(id: Int): Book?

    fun increaseInventory(id: Int, num: Int)

    fun decreaseInventory(id: Int, num: Int)

    fun updateBook(book: Book)

    fun addBook(book: Book): Book

    fun deleteBook(id: Int)
}
