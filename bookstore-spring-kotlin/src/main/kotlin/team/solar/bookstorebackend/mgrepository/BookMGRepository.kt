package team.solar.bookstorebackend.mgrepository

import org.springframework.data.mongodb.repository.MongoRepository
import team.solar.bookstorebackend.entity.Book

interface BookMGRepository : MongoRepository<Book, Int> {
    fun getBooksByType(type: String): List<Book>

    fun getBooksByName(name: String): List<Book>

    fun getBooksByNameContaining(name: String): List<Book>

    fun getBooksByAuthorContaining(author: String): List<Book>

    fun getBooksByDescriptionContaining(description: String): List<Book>

    fun getBookById(id: Int?): Book?
}