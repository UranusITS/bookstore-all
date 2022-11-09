package team.solar.bookstorebackend.daoimpl

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.BookDao
import team.solar.bookstorebackend.entity.Book
import team.solar.bookstorebackend.esrepository.BookESRepository
import team.solar.bookstorebackend.repository.BookRepository
import java.util.*
import javax.transaction.Transactional

@Repository
class BookDaoImpl(val repo: BookRepository, val esRepo: BookESRepository) : BookDao {

    @Cacheable("book-all")
    override fun findAll(): List<Book> {
        val ret = esRepo.findAll().toList()
        println("from esRepo: $ret")
        return ret
    }

    @Cacheable("book-all-types")
    override fun getTypes(): List<String> = repo.getTypes()

    @Cacheable("book-type")
    override fun getBooksByType(type: String) = esRepo.findByType(type)

    override fun getBooksByName(name: String) = esRepo.findByName(name)

    override fun getBooksByNameContaining(name: String) = esRepo.findByNameContaining(name)

    override fun getBooksByAuthorContaining(author: String) = esRepo.findByAuthorContaining(author)

    override fun getBooksByDescriptionContaining(description: String) = esRepo.findByDescriptionContaining(description)

    @Cacheable("book-id")
    override fun getBookById(id: Int?): Book? = esRepo.findById(id!!).get()

    @Transactional(Transactional.TxType.REQUIRED)
    @Caching(
        put = [CachePut("book-id", key = "#book.id")],
        evict = [
            CacheEvict("book-all", allEntries = true),
            CacheEvict("book-all-types", allEntries = true),
            CacheEvict("book-type", key = "#book.type")
        ]
    )
    override fun save(book: Book): Book {
        esRepo.save(book)
        return repo.save(book)
    }

    @Caching(
        evict = [
            CacheEvict("book-id", key = "#id"),
            CacheEvict("book-all", allEntries = true),
            CacheEvict("book-all-types", allEntries = true),
            CacheEvict("book-type", allEntries = true)
        ]
    )
    override fun deleteById(id: Int) {
        esRepo.deleteById(id)
        return repo.deleteById(id)
    }
}