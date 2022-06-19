package page

import antd.layout.layout
import component.AdminPanelComponent
import component.FooterComponent
import component.HeaderComponent
import react.Props
import react.dom.div
import react.fc


val adminPage = fc<Props> {
    div {
        layout {
            child(HeaderComponent::class) { }
            child(AdminPanelComponent::class) { }
            FooterComponent { }
        }
    }
}
