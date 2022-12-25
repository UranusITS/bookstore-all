package team.solar.bookstorebackend.daoimpl

import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.TypeDao
import team.solar.bookstorebackend.entity.Type
import team.solar.bookstorebackend.neorepository.TypeNeoRepository

@Repository
class TypeDaoImpl(private val repo: TypeNeoRepository) : TypeDao {
    override fun findByName(name: String) = repo.findByName(name)

    override fun findByLikesName(name: String) = repo.findByLikesName(name)
}