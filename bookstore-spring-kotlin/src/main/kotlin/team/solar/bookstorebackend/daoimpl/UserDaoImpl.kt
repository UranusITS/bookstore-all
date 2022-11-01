package team.solar.bookstorebackend.daoimpl

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.UserDao
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.repository.UserRepository

@Repository
class UserDaoImpl(val repo: UserRepository) : UserDao {
    @Cacheable("user-id")
    override fun getUserById(id: Int?) = repo.getUserById(id)

    @Cacheable("user-all")
    override fun findAll() = repo.findAll().toList()

    override fun getUserByUsernameAndPassword(username: String?, password: String?) =
        repo.getUserByUsernameAndPassword(username, password)

    override fun countUsersByUsername(username: String?) = repo.countUsersByUsername(username)

    @Caching(
        put = [CachePut("user-id", key = "#user.id")],
        evict = [CacheEvict("user-all", allEntries = true)]
    )
    override fun save(user: User) = repo.save(user)

    @Caching(
        evict = [CacheEvict("user-id", key = "#id"), CacheEvict("user-all", allEntries = true)]
    )
    override fun deleteById(id: Int) = repo.deleteById(id)
}
