package team.solar.bookstorebackend.neorepository

import org.springframework.data.neo4j.repository.Neo4jRepository
import team.solar.bookstorebackend.entity.Type

interface TypeNeoRepository: Neo4jRepository<Type, Long> {
    fun findByName(name: String): Type

    fun findByLikesName(name: String): List<Type>
}