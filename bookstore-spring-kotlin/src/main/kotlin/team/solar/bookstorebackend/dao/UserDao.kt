package team.solar.bookstorebackend.dao

import team.solar.bookstorebackend.entity.User

interface UserDao {
    fun getUserById(id: Int?): User?

    fun findAll(): List<User>

    fun getUserByUsernameAndPassword(username: String?, password: String?): User?

    fun countUsersByUsername(username: String?): Int

    fun save(user: User): User

    fun deleteById(id: Int)
}
