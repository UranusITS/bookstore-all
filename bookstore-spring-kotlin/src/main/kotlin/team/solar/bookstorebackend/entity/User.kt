package team.solar.bookstorebackend.entity

import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id val id: Int? = null,
    val username: String? = null,
    val password: String? = null,
    val auth_level: Int? = null
)
