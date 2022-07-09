package team.solar.bookstorebackend.daoimpl

import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.UserDao
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.repository.UserRepository

@Repository
class UserDaoImpl(val repo: UserRepository) : UserDao {
    override fun getUserById(id: Int?) = repo.getUserById(id)

    override fun findAll() = repo.findAll().toList()

    override fun getUserByUsernameAndPassword(username: String?, password: String?) =
        repo.getUserByUsernameAndPassword(username, password)

    override fun countUsersByUsername(username: String?) = repo.countUsersByUsername(username)

    override fun save(user: User) = repo.save(user)
}
