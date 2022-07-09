package page

import antd.layout.layout
import component.FooterComponent
import component.HeaderComponent
import component.OrderListComponent
import react.Props
import react.dom.div
import react.fc

val orderPage = fc<Props> {
    div {
        layout {
            child(HeaderComponent::class) { }
            child(OrderListComponent::class) { }
            FooterComponent { }
        }
    }
}
