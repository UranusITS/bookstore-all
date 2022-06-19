package data

import kotlinx.serialization.Serializable
import react.Props
import react.State


@Serializable
data class User(val id: Int, val username: String, val password: String, val auth_level: Int) {
    constructor(props: UserProps) : this(props.id, props.username, "", props.authLevel)

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
