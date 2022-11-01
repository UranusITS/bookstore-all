package team.solar.bookstorebackend.daoimpl

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.BookDao
import team.solar.bookstorebackend.entity.Book
import team.solar.bookstorebackend.repository.BookRepository
import javax.transaction.Transactional

@Repository
class BookDaoImpl(val repo: BookRepository) : BookDao {

    @Cacheable("book-all")
    override fun findAll(): List<Book> //= repo.findAll().toList()
    {
        println("get books from database")
        return repo.findAll().toList()
    }

    @Cacheable("book-all-types")
    override fun getTypes(): List<String> = repo.getTypes()

    @Cacheable("book-type")
    override fun getBooksByType(type: String) = repo.getBooksByType(type)

    override fun getBooksByName(name: String) = repo.getBooksByName(name)

    override fun getBooksByAuthor(author: String) = repo.getBooksByAuthor(author)

    override fun getBooksByDescription(description: String) = repo.getBooksByDescription(description)

    @Cacheable("book-id")
    override fun getBookById(id: Int?) = repo.getBookById(id)

    @Transactional(Transactional.TxType.REQUIRED)
    @Caching(
        put = [CachePut("book-id", key = "#book.id")],
        evict = [
            CacheEvict("book-all", allEntries = true),
            CacheEvict("book-all-types", allEntries = true),
            CacheEvict("book-type", key = "#book.type")
        ]
    )
    override fun save(book: Book) = repo.save(book)

    @Caching(
        evict = [
            CacheEvict("book-id", key = "#id"),
            CacheEvict("book-all", allEntries = true),
            CacheEvict("book-all-types", allEntries = true),
            CacheEvict("book-type", allEntries = true)
        ]
    )
    override fun deleteById(id: Int) = repo.deleteById(id)
}