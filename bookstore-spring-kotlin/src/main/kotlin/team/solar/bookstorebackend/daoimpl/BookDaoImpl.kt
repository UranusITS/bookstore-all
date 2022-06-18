package team.solar.bookstorebackend.daoimpl

import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.BookDao
import team.solar.bookstorebackend.entity.Book
import team.solar.bookstorebackend.repository.BookRepository

@Repository
class BookDaoImpl(val repo: BookRepository) : BookDao {

    override fun findAll() = repo.findAll().toList()

    override fun getTypes(): List<String> = repo.getTypes()

    override fun getBooksByType(type: String) = repo.getBooksByType(type)

    override fun getBooksByText(text: String) = repo.getBooksByText(text)

    override fun getBookById(id: Int) = repo.getBookById(id)

    override fun increaseInventoryById(id: Int, num: Int) = repo.increaseInventoryById(id, num)

    override fun decreaseInventoryById(id: Int, num: Int) = repo.decreaseInventoryById(id, num)

    override fun updateAllById(
        id: Int, isbn: String, name: String, type: String, author: String, price: Double,
        description: String, inventory: Int, img_path: String
    ) = repo.updateAllById(id, isbn, name, type, author, price, description, inventory, img_path)

    override fun save(book: Book) = repo.save(book)

    override fun deleteById(id: Int) = repo.deleteById(id)
}