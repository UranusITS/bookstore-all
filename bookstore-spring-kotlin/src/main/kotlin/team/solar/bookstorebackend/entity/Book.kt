package team.solar.bookstorebackend.entity

import org.springframework.data.elasticsearch.annotations.*
import javax.persistence.*

@Entity
@Table(name = "books")
// @Document(indexName = "book", indexStoreType = "book")
class Book(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id val id: Int? = null,
    // @Field(type = FieldType.Keyword)
    val isbn: String? = null,
    // @Field(type = FieldType.Text, analyzer="ik_max_word")
    val name: String? = null,
    // @Field(type = FieldType.Keyword)
    val type: String? = null,
    // @Field(type = FieldType.Text, analyzer="ik_max_word")
    val author: String? = null,
    // @Field(type = FieldType.Double)
    val price: Double? = null,
    // @Field(type = FieldType.Text, analyzer="ik_max_word")
    val description: String? = null,
    // @Field(type = FieldType.Long)
    var inventory: Int? = null,
    // @Field(type = FieldType.Keyword)
    val img_path: String? = null
)
