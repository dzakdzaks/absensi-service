package com.dzakdzaks.absensi.data.role

import com.dzakdzaks.absensi.data.base.InternalServerException
import com.dzakdzaks.absensi.data.role.model.Role
import com.dzakdzaks.absensi.util.toResult
import io.ktor.server.plugins.*
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

interface RoleRepository {
    suspend fun createRole(role: Role): Result<Role>
    suspend fun getRoles(): Result<List<Role>>
    suspend fun getRoleByName(name: String): Result<Role>
    suspend fun getRoleById(id: String): Result<Role>
}

class RoleRepositoryImpl(
    coroutineDatabase: CoroutineDatabase
) : RoleRepository {

    private val collection = coroutineDatabase.getCollection<Role>()

    override suspend fun createRole(role: Role): Result<Role> {
        if (role.name.isNullOrEmpty()) throw BadRequestException("Name is empty")
        val isExist = getRoleByName(role.name)
        return if (isExist.isSuccess) {
            throw BadRequestException("Role already exist")
        } else {
            if (collection.insertOne(role).wasAcknowledged()) {
                role.toResult()
            } else {
                throw InternalServerException("Create role failed")
            }
        }
    }

    override suspend fun getRoleByName(name: String): Result<Role> {
        return collection.findOne(Role::name eq name).toResult()
    }

    override suspend fun getRoleById(id: String): Result<Role> {
        val bsonId: Id<Role> = ObjectId(id).toId()
        return collection.findOne(Role::id eq bsonId).toResult()
    }

    override suspend fun getRoles(): Result<List<Role>> {
        return collection.find().toList().toResult()
    }

}