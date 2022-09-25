package team.solar.bookstorebackend.serviceimpl

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import org.springframework.web.context.WebApplicationContext
import team.solar.bookstorebackend.dao.UserDao
import team.solar.bookstorebackend.entity.Book
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.service.UserService

@Service
class UserServiceImpl(val dao: UserDao) : UserService {
    override fun findAll(): List<User> = dao.findAll()
    override fun getUserByUsernameAndPassword(username: String?, password: String?) =
        dao.getUserByUsernameAndPassword(username, password)

    override fun countUsersByUsername(username: String?) = dao.countUsersByUsername(username)

    override fun addUser(user: User) = dao.save(user)

    override fun updateUserAuth(user: User) {
        val oldUser = dao.getUserById(user.id)
        if (oldUser != null) {
            dao.save(user)
        }
    }

    override fun deleteById(id: Int) = dao.deleteById(id)
}
