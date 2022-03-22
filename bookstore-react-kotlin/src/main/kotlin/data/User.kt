package data

import react.*


data class User(val id: Int, val name: String) {
    constructor(props: UserProps) : this(props.id, props.name)
}

external interface UserProps : Props {
    var id: Int
    var name: String
}
