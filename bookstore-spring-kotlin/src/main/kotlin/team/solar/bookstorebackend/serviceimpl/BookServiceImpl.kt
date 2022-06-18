package team.solar.bookstorebackend.serviceimpl

import org.springframework.stereotype.Service
import team.solar.bookstorebackend.dao.BookDao
import team.solar.bookstorebackend.entity.Book
import team.solar.bookstorebackend.service.BookService

@Service
class BookServiceImpl(val dao: BookDao) : BookService {
    override fun findAll(): List<Book> = dao.findAll().toList()

    override fun getTypes(): List<String> = dao.getTypes()

    override fun getBooksByType(type: String) = dao.getBooksByType(type)

    override fun getBooksByTypes(types: List<String>): List<Book> {
        val re = mutableListOf<Book>()
        for (type in types) {
            re.addAll(dao.getBooksByType(type))
        }
        return re.toList()
    }

    override fun getBooksByText(text: String) = dao.getBooksByText(text)

    override fun getBookById(id: Int) = dao.getBookById(id)

    override fun increaseInventory(id: Int, num: Int) = dao.increaseInventoryById(id, num)

    override fun decreaseInventory(id: Int, num: Int) = dao.decreaseInventoryById(id, num)

    override fun updateBook(book: Book) {
        book.id?.let {
            dao.updateAllById(
                it, book.isbn, book.name, book.type, book.author, book.price,
                book.description, book.inventory, book.img_path
            )
        }
    }

    override fun addBook(book: Book) = dao.save(book)

    override fun deleteBook(id: Int) = dao.deleteById(id)
}
