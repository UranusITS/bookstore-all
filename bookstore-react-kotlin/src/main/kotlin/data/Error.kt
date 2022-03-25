package data

import react.Props


val errorCodeToInfo = mapOf(404 to "Not Found")

external interface ErrorProps : Props {
    var errorCode: Int
    var extraInfo: String
}
