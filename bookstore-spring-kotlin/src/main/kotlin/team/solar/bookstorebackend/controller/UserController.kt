package team.solar.bookstorebackend.controller

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.solar.bookstorebackend.entity.Book
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.service.UserService

@RestController
@RequestMapping("/user")
class UserController(val service: UserService) {
    @RequestMapping("/users")
    fun findAll(): List<User> = service.findAll()

    @RequestMapping("/login")
    fun login(@RequestParam("username") username: String, @RequestParam("password") password: String): User {
        val user = service.getUserByUsernameAndPassword(username, password)
        return user ?: User(-1, "", "", -1)
    }

    @RequestMapping("/register")
    fun register(@RequestParam("username") username: String, @RequestParam("password") password: String): User {
        return if (service.countUsersByUsername(username) != 0)
            User(-1, "", "", -1)
        else
            service.addUser(User(null, username, password, 0))
    }

    @RequestMapping("/update-user-auth")
    fun updateUserAuth(@RequestBody user: User) = service.updateUserAuth(user)

    @RequestMapping("/delete")
    fun deleteById(@RequestParam("id") id: Int) = service.deleteById(id)
}
