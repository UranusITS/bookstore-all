package component

import antd.card.card
import data.*
import kotlinext.js.js
import react.fc
import style.ConsumptionItemStyles
import styled.*
import kotlin.math.roundToInt


val UserConsumptionItemComponent = fc<ConsumptionProps> { props ->
    if (props.id != -1) {
        styledDiv {
            card {
                attrs.bordered = true
                attrs.style = js {
                    margin = "0 auto"
                    width = 960
                }
                styledDiv {
                    css {
                        +ConsumptionItemStyles.inline
                        +ConsumptionItemStyles.userId
                    }
                    +props.id.toString()
                }
                styledDiv {
                    css {
                        +ConsumptionItemStyles.inline
                        +ConsumptionItemStyles.username
                    }
                    +props.username
                }
                styledDiv {
                    css {
                        +ConsumptionItemStyles.inline
                        +ConsumptionItemStyles.consumption
                    }
                    val consumption = (props.consumption * 100).roundToInt().toDouble() / 100
                    +"￥$consumption"
                }
            }
        }
    }
}
