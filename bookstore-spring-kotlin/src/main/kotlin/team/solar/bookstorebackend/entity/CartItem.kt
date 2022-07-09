package team.solar.bookstorebackend.entity

import javax.persistence.*

@Entity
@Table(name = "cart_items")
class CartItem(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id val id: Int? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User? = null,
    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    val book: Book? = null,
    var num: Int? = null,
    var checked: Boolean? = null
)
