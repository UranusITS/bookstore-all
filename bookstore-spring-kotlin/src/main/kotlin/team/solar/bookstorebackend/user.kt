package team.solar.bookstorebackend

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@Table("users")
data class User(
    @Id val id: Int?,
    val username: String,
    val password: String,
    val auth_level: Int
)

interface UserRepository : CrudRepository<User, Int> {
    @Query("select * from users where username = :username and password = :password")
    fun getUserByUsernameAndPassword(@Param("username") username: String, @Param("password") password: String): User?

    @Query("select count(*) from users where username = :username")
    fun countUserByUsername(@Param("username") username: String): Int
}

@Service
class UserService(val db: UserRepository) {
    fun getUserByUsernameAndPassword(username: String, password: String) =
        db.getUserByUsernameAndPassword(username, password)

    fun countUserByUsername(username: String) = db.countUserByUsername(username)

    fun addUser(user: User) = db.save(user)
}

@RestController
@CrossOrigin
@RequestMapping("/user")
class UserResource(val service: UserService) {
    @RequestMapping("/login")
    fun login(@RequestParam("username") username: String, @RequestParam("password") password: String): User {
        val user = service.getUserByUsernameAndPassword(username, password)
        return user ?: User(-1, "", "", -1)
    }

    @RequestMapping("/register")
    fun register(@RequestParam("username") username: String, @RequestParam("password") password: String): User {
        return if (service.countUserByUsername(username) != 0)
            User(-1, "", "", -1)
        else
            service.addUser(User(null, username, password, 0))
    }
}
