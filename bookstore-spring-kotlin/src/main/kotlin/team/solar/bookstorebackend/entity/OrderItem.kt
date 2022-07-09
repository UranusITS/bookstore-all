package team.solar.bookstorebackend.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "order_items")
class OrderItem(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id val id: Int? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    var order: Order? = null,
    val name: String? = null,
    val author: String? = null,
    val price: Double? = null,
    val num: Int? = null,
    val img_path: String? = null
)
