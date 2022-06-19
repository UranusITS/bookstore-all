package team.solar.bookstorebackend.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import team.solar.bookstorebackend.entity.User

interface UserRepository : CrudRepository<User, Int> {
    fun getUserByUsernameAndPassword(username: String, password: String): User?

    fun countUsersByUsername(username: String): Int

    @Modifying
    @Query("update users set auth_level = :auth_level where id = :id")
    fun updateAuthById(@Param("id") id: Int, @Param("auth_level") auth_level: Int)
}
