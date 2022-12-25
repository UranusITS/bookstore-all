package data

import kotlinext.js.js
import kotlinx.coroutines.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import react.Props
import react.State
import web.http.fetchAsync
import web.http.RequestInit
import web.storage.localStorage


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
    return Json.decodeFromString(
        fetchAsync(
            "$backendUrl/user/users",
            js { credentials = INCLUDE } as RequestInit
        ).await().text().await()
    )
}

suspend fun deleteUserById(id: Int) {
    fetchAsync("$backendUrl/user/delete?id=$id", js { credentials = INCLUDE } as RequestInit).await()
}

suspend fun checkLogin(username: String, password: String): User? {
    return Json.decodeFromString(fetchAsync(
        "$backendUrl/user/login?username=$username&password=$password",
        js { credentials = INCLUDE } as RequestInit
    ).await().text().await())
}

suspend fun checkRegister(username: String, password: String): User? {
    if (username.isEmpty()) {
        return null
    }
    if (password.length < 6) {
        return null
    }
    val user = Json.decodeFromString<User>(fetchAsync(
        "$backendUrl/user/register?username=${username}&password=${password}",
        js { credentials = INCLUDE } as RequestInit
    ).await().text().await())
    return if (user.id != -1) user else null
}
