package component

import antd.button.button
import antd.result.result
import data.ErrorProps
import data.errorCodeToInfo
import history.createHashHistory
import react.buildElement
import react.fc


val errorComponent = fc<ErrorProps> { props ->
    val history = createHashHistory()
    result {
        attrs {
            status = props.errorCode
            title = "${props.errorCode} ${errorCodeToInfo[props.errorCode]}"
            subTitle = props.extraInfo
            extra = buildElement {
                button {
                    attrs.type = "primary"
                    attrs.onClick = {
                        history.back()
                    }
                    +"回到上一页"
                }
            }
        }
    }
}
