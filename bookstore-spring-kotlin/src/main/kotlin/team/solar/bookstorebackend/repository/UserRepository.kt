package team.solar.bookstorebackend.repository

import org.springframework.data.repository.CrudRepository
import team.solar.bookstorebackend.entity.User

interface UserRepository : CrudRepository<User, Int> {
    fun getUserByUsernameAndPassword(username: String, password: String): User?

    fun countUsersByUsername(username: String): Int
}
