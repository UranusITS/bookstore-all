package page

import antd.layout.layout
import component.FooterComponent
import component.HeaderComponent
import component.SettlementComponent
import react.Props
import react.dom.div
import react.fc

val settlementPage = fc<Props> {
    div {
        layout {
            child(HeaderComponent::class) { }
            child(SettlementComponent::class) { }
            FooterComponent { }
        }
    }
}
