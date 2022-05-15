package page

import antd.layout.layout
import component.CartComponent
import component.FooterComponent
import component.HeaderComponent
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
