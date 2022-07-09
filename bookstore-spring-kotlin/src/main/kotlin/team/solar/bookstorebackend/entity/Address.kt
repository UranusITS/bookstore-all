package team.solar.bookstorebackend.entity

import javax.persistence.*

@Entity
@Table(name = "address")
class Address(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id val id: Int? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User? = null,
    val name: String? = null,
    val phone_number: String? = null,
    var address_detail: String? = null
)
