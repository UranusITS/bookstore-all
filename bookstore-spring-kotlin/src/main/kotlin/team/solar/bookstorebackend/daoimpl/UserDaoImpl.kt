package team.solar.bookstorebackend.daoimpl

import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.UserDao
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.repository.UserRepository

@Repository
class UserDaoImpl(val repo: UserRepository) : UserDao {
    override fun findAll(): List<User> = repo.findAll().toList()

    override fun getUserByUsernameAndPassword(username: String, password: String): User? =
        repo.getUserByUsernameAndPassword(username, password)

    override fun countUsersByUsername(username: String): Int = repo.countUsersByUsername(username)

    override fun save(user: User) = repo.save(user)

    override fun updateAuthById(id: Int, auth_level: Int) = repo.updateAuthById(id, auth_level)
}
