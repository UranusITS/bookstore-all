package team.solar.bookstorebackend.serviceimpl

import org.springframework.stereotype.Service
import team.solar.bookstorebackend.dao.BookDao
import team.solar.bookstorebackend.dao.TypeDao
import team.solar.bookstorebackend.entity.Book
import team.solar.bookstorebackend.service.BookService

@Service
class BookServiceImpl(val dao: BookDao, val typeDao: TypeDao) : BookService {
    override fun findAll(): List<Book> = dao.findAll().toList()

    override fun getTypes(): List<String> = dao.getTypes()

    override fun getBooksByType(type: String) = dao.getBooksByType(type)

    override fun getBooksByTypes(types: List<String>): List<Book> {
        val re = mutableListOf<Book>()
        val newTypes = mutableListOf<String>()
        newTypes.addAll(types)
        for (type in types) {
            val likes = typeDao.findByLikesName(type)
            for (like in likes) {
                if (!newTypes.contains(like.name)) {
                    like.name?.let { newTypes.add(it) }
                }
            }
        }
        for (type in newTypes) {
            re.addAll(dao.getBooksByType(type))
        }
        return re.toList()
    }

    override fun getBooksByText(text: String): List<Book> {
        val books = mutableListOf<Book>()
        books.addAll(dao.getBooksByNameContaining(text))
        books.addAll(dao.getBooksByAuthorContaining(text))
        books.addAll(dao.getBooksByDescriptionContaining(text))
        return books.toSet().toList()
    }

    override fun getBookById(id: Int) = dao.getBookById(id)

    override fun increaseInventory(id: Int, num: Int) {
        val book = dao.getBookById(id)
        if (book != null) {
            book.inventory = book.inventory?.plus(num)
            dao.save(book)
        }
    }

    override fun decreaseInventory(id: Int, num: Int) {
        val book = dao.getBookById(id)
        if (book != null) {
            book.inventory = book.inventory?.minus(num)
            dao.save(book)
        }
    }

    override fun updateBook(book: Book) {
        val oldBook = dao.getBookById(book.id)
        if (oldBook != null) {
            dao.save(book)
        }
    }

    override fun addBook(book: Book) = dao.save(book)

    override fun deleteBook(id: Int) = dao.deleteById(id)
}
