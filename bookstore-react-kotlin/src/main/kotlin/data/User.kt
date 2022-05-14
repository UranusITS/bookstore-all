package data

import kotlinx.serialization.Serializable
import react.Props


@Serializable
data class User(val id: Int, val username: String, val password: String, val auth_level: Int) {
    constructor(props: UserProps) : this(props.id, props.username, "", props.authLevel)
}

external interface UserProps : Props {
    var id: Int
    var username: String
    var authLevel: Int
}
