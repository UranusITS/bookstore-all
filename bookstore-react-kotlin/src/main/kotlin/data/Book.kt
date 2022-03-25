package data

import react.Props
import kotlinx.serialization.Serializable


@Serializable
data class Book(val id: Int, val isbn: String, val name: String, val type: String, val author: String,
                val price: Double, val description: String, val inventory: Int, val imgPath: String)
{
    constructor(props: BookProps) :
            this(props.id, props.isbn, props.name, props.type, props.author,
                 props.price, props.description, props.inventory, props.imgPath)
}

external interface BookProps : Props {
    var id: Int
    var isbn: String
    var name: String
    var type: String
    var author: String
    var price: Double
    var description: String
    var inventory: Int
    var imgPath: String
}
