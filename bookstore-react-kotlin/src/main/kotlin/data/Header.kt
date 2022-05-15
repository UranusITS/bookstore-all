package data

import react.State


data class HeaderState(
    var user: User, var typedInName: String, var typedInPassword: String,
    var isAuthored: Boolean, var isModalVisible: Boolean
) : State
