package data

import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.INCLUDE
import org.w3c.fetch.RequestCredentials
import org.w3c.fetch.RequestInit
import react.Props
import react.State


@Serializable
data class User(
    var id: Int? = null,
    val username: String? = null,
    val password: String? = null,
    val auth_level: Int? = null
) {
    constructor(state: UserState) : this(state.id, state.username, "", state.auth_level)
}

data class UserInfoState(var users: List<User>) : State

data class UserState(var id: Int, var username: String, var auth_level: Int) : State {
    constructor(props: UserProps) : this(props.id, props.username, props.authLevel)
}

external interface UserProps : Props {
    var id: Int
    var username: String
    var authLevel: Int
}

data class UserConsumptionState(var userConsumptionPairList: List<Pair<User, Double>>) : State

external interface ConsumptionProps : Props {
    var id: Int
    var username: String
    var consumption: Double
}

fun getLocalUser(): User? {
    val userJson = localStorage.getItem("user")
    return if (!userJson.isNullOrEmpty()) Json.decodeFromString(userJson) else null
}

fun setLocalUser(user: User?) {
    localStorage.setItem("user", Json.encodeToString(user))
}

suspend fun getAllUsers(): List<User> {
    val response = window.fetch(
        "$backendUrl/user/users",
        RequestInit(credentials = RequestCredentials.Companion.INCLUDE)
    )
        .await()
        .text()
        .await()
    return Json.decodeFromString(response)
}

suspend fun deleteUserById(id: Int) {
    window.fetch(
        "$backendUrl/user/delete?id=$id",
        RequestInit(credentials = RequestCredentials.Companion.INCLUDE)
    )
        .await()
        .text()
        .await()
}

suspend fun checkLogin(username: String, password: String): User? {
    val response =
        window.fetch(
            "$backendUrl/user/login?username=${username}&password=${password}",
            RequestInit(credentials = RequestCredentials.INCLUDE)
        )
            .await()
            .text()
            .await()
    val user = Json.decodeFromString<User>(response)
    return if (user.id != -1) user else null
}

suspend fun checkRegister(username: String, password: String): User? {
    if (username.isEmpty()) {
        return null
    }
    if (password.length < 6) {
        return null
    }
    val response =
        window.fetch(
            "$backendUrl/user/register?username=${username}&password=${password}",
            RequestInit(credentials = RequestCredentials.Companion.INCLUDE)
        )
            .await()
            .text()
            .await()
    val user = Json.decodeFromString<User>(response)
    return if (user.id != -1) user else null
}
