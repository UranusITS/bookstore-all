package team.solar.bookstorebackend.entity

import javax.persistence.*

@Entity
@Table(name = "books")
class Book(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id val id: Int? = null,
    val isbn: String? = null,
    val name: String? = null,
    val type: String? = null,
    val author: String? = null,
    val price: Double? = null,
    val description: String? = null,
    var inventory: Int? = null,
    val img_path: String? = null
)
