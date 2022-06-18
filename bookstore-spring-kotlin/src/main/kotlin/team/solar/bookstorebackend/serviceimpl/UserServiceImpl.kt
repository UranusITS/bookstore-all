package team.solar.bookstorebackend.serviceimpl

import org.springframework.stereotype.Service
import team.solar.bookstorebackend.dao.UserDao
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.service.UserService

@Service
class UserServiceImpl(val dao: UserDao) : UserService {
    override fun getUserByUsernameAndPassword(username: String, password: String) =
        dao.getUserByUsernameAndPassword(username, password)

    override fun countUsersByUsername(username: String) = dao.countUsersByUsername(username)

    override fun addUser(user: User) = dao.save(user)
}
