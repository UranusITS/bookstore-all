package team.solar.bookstorebackend.dao

import team.solar.bookstorebackend.entity.Book

interface BookDao {
    fun findAll(): List<Book>

    fun getTypes(): List<String>

    fun getBooksByType(type: String): List<Book>

    fun getBooksByName(name: String): List<Book>

    fun getBooksByNameContaining(name: String): List<Book>

    fun getBooksByAuthorContaining(author: String): List<Book>

    fun getBooksByDescriptionContaining(description: String): List<Book>

    fun getBookById(id: Int?): Book?

    fun save(book: Book): Book

    fun deleteById(id: Int)
}