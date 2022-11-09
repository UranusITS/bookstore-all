package team.solar.bookstorebackend.esrepository

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import team.solar.bookstorebackend.entity.Book

interface BookESRepository: ElasticsearchRepository<Book, Int> {
    fun findByIsbn(isbn: String): List<Book>

    fun findByName(name: String): List<Book>

    fun findByType(type: String): List<Book>

    fun findByAuthor(author: String): List<Book>

    fun findByNameContaining(name: String): List<Book>

    fun findByTypeContaining(type: String): List<Book>

    fun findByAuthorContaining(author: String): List<Book>

    fun findByDescriptionContaining(description: String): List<Book>
}