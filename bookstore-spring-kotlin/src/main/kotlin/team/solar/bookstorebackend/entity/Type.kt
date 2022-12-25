package team.solar.bookstorebackend.entity

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
class Type {
    @Id
    @GeneratedValue
    val id: Long? = null
    val name: String? = null
    @Relationship(type="Like")
    val likes: List<Type>? = null
}