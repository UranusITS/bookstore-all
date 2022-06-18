package team.solar.bookstorebackend.dao

import team.solar.bookstorebackend.entity.Book

interface BookDao {
    fun findAll(): List<Book>

    fun getTypes(): List<String>

    fun getBooksByType(type: String): List<Book>

    fun getBooksByText(text: String): List<Book>

    fun getBookById(id: Int): Book?

    fun increaseInventoryById(id: Int, num: Int)

    fun decreaseInventoryById(id: Int, num: Int)

    fun updateAllById(
        id: Int, isbn: String, name: String, type: String, author: String, price: Double,
        description: String, inventory: Int, img_path: String
    )

    fun save(book: Book): Book

    fun deleteById(id: Int)
}