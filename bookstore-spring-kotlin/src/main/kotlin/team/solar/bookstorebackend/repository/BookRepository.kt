package team.solar.bookstorebackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.entity.Book

@Repository
interface BookRepository : JpaRepository<Book, Int>, JpaSpecificationExecutor<Book> {
    @Query("select distinct type from Book")
    fun getTypes(): List<String>

    fun getBooksByType(type: String): List<Book>

    fun getBooksByName(name: String): List<Book>

    fun getBooksByAuthor(author: String): List<Book>

    fun getBooksByDescription(description: String): List<Book>

    fun getBookById(id: Int?): Book?
}
