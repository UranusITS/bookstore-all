package component

import antd.button.button
import antd.card.card
import antd.icon.deleteOutlined
import antd.inputnumber.inputNumber
import data.SettlementItemProps
import react.buildElement
import react.fc
import style.SettlementItemStyles
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledP


val SettlementItemComponent = fc<SettlementItemProps> { props ->
    styledDiv {
        card {
            attrs.bordered = true
            attrs.style = kotlinext.js.js {
                margin = "0 auto"
                width = 1200
            }
            styledDiv {
                css { +SettlementItemStyles.inline }
                styledImg {
                    attrs.width = "128px"
                    attrs.src = props.book.imgPath
                }
            }
            styledDiv {
                css {
                    +SettlementItemStyles.inline
                    +SettlementItemStyles.textInfo
                }
                styledP {
                    css { +SettlementItemStyles.bookName }
                    +props.book.name
                }
                styledP {
                    css { +SettlementItemStyles.author }
                    +props.book.author
                }
            }
            styledDiv {
                css {
                    +SettlementItemStyles.inline
                    +SettlementItemStyles.numSelect
                }
                styledDiv {
                    css { +SettlementItemStyles.inline }
                    styledP {
                        css { +SettlementItemStyles.numInfo }
                        +"购买"
                    }
                }
                styledDiv {
                    css { +SettlementItemStyles.inline }
                    inputNumber {
                        attrs.size = "large"
                        attrs.min = 1
                        attrs.max = props.book.inventory
                        attrs.defaultValue = props.num
                    }
                }
                styledDiv {
                    css { +SettlementItemStyles.inline }
                    styledP {
                        css { +SettlementItemStyles.numInfo }
                        +"本"
                    }
                }
            }
            styledDiv {
                css {
                    +SettlementItemStyles.inline
                    +SettlementItemStyles.price
                }
                styledP { +"￥${props.num * props.book.price}" }
            }
            styledDiv {
                css {
                    +SettlementItemStyles.inline
                    +SettlementItemStyles.deleteIcon
                }
                button {
                    attrs.size = "large"
                    attrs.icon = buildElement {
                        deleteOutlined { }
                    }
                }
            }
        }
    }
}
