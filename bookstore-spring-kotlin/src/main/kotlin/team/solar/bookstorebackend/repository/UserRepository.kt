package team.solar.bookstorebackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.entity.User

@Repository
interface UserRepository : JpaRepository<User, Int>, JpaSpecificationExecutor<User> {
    fun getUserById(id: Int?): User?

    fun getUserByUsernameAndPassword(username: String?, password: String?): User?

    fun countUsersByUsername(username: String?): Int
}
