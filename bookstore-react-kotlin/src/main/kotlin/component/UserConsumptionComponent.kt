package component

import antd.grid.col
import antd.grid.row
import data.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import react.*


class UserConsumptionComponent(props: Props) : RComponent<Props, UserConsumptionState>(props) {
    init {
        state = UserConsumptionState(listOf())
    }

    override fun componentDidMount() {
        GlobalScope.launch {
            val users = getAllUsers()
            val orders = getAllOrders()
            val userConsumptionPairs = mutableListOf<Pair<User, Double>>()
            for (user in users) {
                userConsumptionPairs.add(Pair(user, 0.0))
            }
            for (order in orders) {
                var totalPrice = 0.0
                for (item in order.orderItems!!) {
                    totalPrice += item.num!! * item.price!!
                }
                for (userConsumptionPair in userConsumptionPairs) {
                    if (userConsumptionPair.first.id == order.user!!.id) {
                        totalPrice += userConsumptionPair.second
                        val tmpPair = Pair(userConsumptionPair.first, totalPrice)
                        userConsumptionPairs.remove(userConsumptionPair)
                        userConsumptionPairs.add(tmpPair)
                        break
                    }
                }
            }
            userConsumptionPairs.sortByDescending { pair: Pair<User, Double> -> pair.second }
            setState(UserConsumptionState(userConsumptionPairs))
        }
    }

    override fun RBuilder.render() {
        row {
            attrs.gutter = 24
            for (pair in state.userConsumptionPairList) {
                console.log(pair)
                col {
                    attrs.span = 24
                    UserConsumptionItemComponent {
                        attrs {
                            id = pair.first.id!!
                            username = pair.first.username!!
                            consumption =pair.second
                        }
                    }
                }
            }
        }
    }
}
