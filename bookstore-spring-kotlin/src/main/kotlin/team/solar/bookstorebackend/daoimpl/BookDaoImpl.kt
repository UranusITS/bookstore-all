package team.solar.bookstorebackend.daoimpl

import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.BookDao
import team.solar.bookstorebackend.entity.Book
import team.solar.bookstorebackend.repository.BookRepository
import javax.transaction.Transactional

@Repository
class BookDaoImpl(val repo: BookRepository) : BookDao {

    override fun findAll() = repo.findAll().toList()

    override fun getTypes(): List<String> = repo.getTypes()

    override fun getBooksByType(type: String) = repo.getBooksByType(type)

    override fun getBooksByName(name: String) = repo.getBooksByName(name)

    override fun getBooksByAuthor(author: String) = repo.getBooksByAuthor(author)

    override fun getBooksByDescription(description: String) = repo.getBooksByDescription(description)

    override fun getBookById(id: Int?) = repo.getBookById(id)

    @Transactional(Transactional.TxType.REQUIRED)
    override fun save(book: Book) = repo.save(book)

    override fun deleteById(id: Int) = repo.deleteById(id)
}