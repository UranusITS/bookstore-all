package team.solar.bookstorebackend.service

import team.solar.bookstorebackend.entity.User

interface UserService {
    fun getUserByUsernameAndPassword(username: String, password: String): User?

    fun countUsersByUsername(username: String): Int

    fun addUser(user: User): User
}
