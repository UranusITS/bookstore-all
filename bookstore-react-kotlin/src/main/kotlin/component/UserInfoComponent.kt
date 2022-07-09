package component

import antd.grid.col
import antd.grid.row
import data.UserInfoState
import data.backendUrl
import data.getAllUsers
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

    override fun componentDidMount() {
        GlobalScope.launch {
            val tmpUsers = getAllUsers()
            console.log(tmpUsers)
            setState { users = tmpUsers }
        }
    }

    override fun RBuilder.render() {
        row {
            attrs.gutter = 24
            for (item in state.users) {
                console.log(item)
                col {
                    attrs.span = 24
                    child(UserInfoItemComponent::class) {
                        attrs {
                            id = item.id!!
                            username = item.username!!
                            authLevel = item.auth_level!!
                        }
                    }
                }
            }
        }
    }
}
