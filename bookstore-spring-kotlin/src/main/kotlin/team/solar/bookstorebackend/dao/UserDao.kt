package team.solar.bookstorebackend.dao

import team.solar.bookstorebackend.entity.User

interface UserDao {
    fun getUserByUsernameAndPassword(username: String, password: String): User?

    fun countUsersByUsername(username: String): Int

    fun save(user: User): User
}