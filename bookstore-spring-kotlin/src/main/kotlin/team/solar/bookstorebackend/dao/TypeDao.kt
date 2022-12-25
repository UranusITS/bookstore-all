package team.solar.bookstorebackend.dao

import team.solar.bookstorebackend.entity.Type

interface TypeDao {
    fun findByName(name: String): Type

    fun findByLikesName(name: String): List<Type>
}