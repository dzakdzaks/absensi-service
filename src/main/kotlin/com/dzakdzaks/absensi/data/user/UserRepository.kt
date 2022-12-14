package com.dzakdzaks.absensi.data.user

import at.favre.lib.crypto.bcrypt.BCrypt
import com.dzakdzaks.absensi.data.base.InternalServerException
import com.dzakdzaks.absensi.data.user.model.User
import com.dzakdzaks.absensi.util.toResult
import io.ktor.server.plugins.*
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

interface UserRepository {
    suspend fun createUser(user: User): Result<User>
    suspend fun getUserByUsername(username: String): Result<User>
    suspend fun getUserById(id: String): Result<User>
    suspend fun getUsers(role: String?, classs: String?): Result<List<User>>
}

class UserRepositoryImpl(
    coroutineDatabase: CoroutineDatabase
) : UserRepository {

    private val collection = coroutineDatabase.getCollection<User>()

    override suspend fun createUser(user: User): Result<User> {
        if (user.username.isNullOrEmpty() || user.password.isNullOrEmpty()) throw BadRequestException("username or password is empty")
        val isExist = getUserByUsername(user.username)
        return if (isExist.isSuccess) {
            throw BadRequestException("User already exist")
        } else {
            val encryptedPassword = BCrypt.withDefaults().hashToString(12, user.password?.toCharArray())
            user.password = encryptedPassword
            if (collection.insertOne(user).wasAcknowledged()) {
                user.toResult()
            } else {
                throw InternalServerException("Create user failed")
            }
        }
    }

    override suspend fun getUserByUsername(username: String): Result<User> {
        return collection.findOne(User::username eq username).toResult()
    }

    override suspend fun getUserById(id: String): Result<User> {
        val bsonId: Id<User> = ObjectId(id).toId()
        return collection.findOne(User::id eq bsonId).toResult()
    }

    override suspend fun getUsers(role: String?, classs: String?): Result<List<User>> {
        val filters: MutableList<Bson> = mutableListOf<Bson>().apply {
            role?.takeIf { add(User::role eq it) }
            classs?.takeIf { add(User::classs eq it) }
        }

        return collection.find(*filters.toTypedArray()).toList().toResult()
    }

}