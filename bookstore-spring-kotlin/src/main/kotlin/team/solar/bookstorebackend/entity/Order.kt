package team.solar.bookstorebackend.entity

import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id val id: Int? = null,
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User? = null,
    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    val address: Address? = null,
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @Column(nullable = true)
    val orderItems: List<OrderItem>? = null
)
