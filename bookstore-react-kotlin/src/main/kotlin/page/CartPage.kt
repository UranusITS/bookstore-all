package page

import antd.layout.layout
import component.FooterComponent
import component.HeaderComponent
import component.CartComponent
import react.Props
import react.dom.div
import react.fc

val cartPage = fc<Props> {
    div {
        layout {
            child(HeaderComponent::class) { }
            child(CartComponent::class) { }
            FooterComponent { }
        }
    }
}
