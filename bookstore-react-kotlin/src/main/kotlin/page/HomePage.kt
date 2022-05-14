package page

import antd.layout.layout
import component.BookListComponent
import component.FooterComponent
import component.HeaderComponent
import react.Props
import react.dom.div
import react.fc


val homePage = fc<Props> {
    div {
        layout {
            child(HeaderComponent::class) { }
            child(BookListComponent::class) { }
            FooterComponent { }
        }
    }
}
