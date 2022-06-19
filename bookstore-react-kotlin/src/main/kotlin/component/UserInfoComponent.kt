package component

import antd.grid.col
import antd.grid.row
import data.UserInfoState
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import react.*


class UserInfoComponent(props: Props) : RComponent<Props, UserInfoState>(props) {
    init {
        state = UserInfoState(listOf())
    }

    private suspend fun fetchUserList() {
        val response = window.fetch("http://localhost:8080/user/users")
            .await()
            .text()
            .await()
        setState { users = Json.decodeFromString(response) }
    }

    override fun componentDidMount() {
        GlobalScope.launch {
            fetchUserList()
        }
    }

    override fun RBuilder.render() {
        row {
            attrs.gutter = 24
            for (item in state.users) {
                col {
                    attrs.span = 24
                    child(UserInfoItemComponent::class) {
                        attrs {
                            id = item.id
                            username = item.username
                            authLevel = item.auth_level
                        }
                    }
                }
            }
        }
    }
}
