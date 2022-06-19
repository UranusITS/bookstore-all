package team.solar.bookstorebackend.dao

import team.solar.bookstorebackend.entity.User

interface UserDao {
    fun findAll(): List<User>

    fun getUserByUsernameAndPassword(username: String, password: String): User?

    fun countUsersByUsername(username: String): Int

    fun save(user: User): User

    fun updateAuthById(id: Int, auth_level: Int)
}
