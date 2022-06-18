package team.solar.bookstorebackend.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("books")
data class Book(
    @Id val id: Int?,
    val isbn: String,
    val name: String,
    val type: String,
    val author: String,
    val price: Double,
    val description: String,
    val inventory: Int,
    val img_path: String
)
