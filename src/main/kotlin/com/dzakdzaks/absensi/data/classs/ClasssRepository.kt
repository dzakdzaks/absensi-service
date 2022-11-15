package com.dzakdzaks.absensi.data.classs

import com.dzakdzaks.absensi.data.base.InternalServerException
import com.dzakdzaks.absensi.data.classs.model.Classs
import com.dzakdzaks.absensi.util.toResult
import io.ktor.server.plugins.*
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

interface ClasssRepository {
    suspend fun createClasss(classs: Classs): Result<Classs>
    suspend fun getClasss(): Result<List<Classs>>
    suspend fun getClasssByName(name: String): Result<Classs>
    suspend fun getClasssById(id: String): Result<Classs>
}

class ClasssRepositoryImpl(
    coroutineDatabase: CoroutineDatabase
) : ClasssRepository {

    private val collection = coroutineDatabase.getCollection<Classs>()

    override suspend fun createClasss(classs: Classs): Result<Classs> {
        val isClassExist = getClasssByName(classs.name)
        return if (isClassExist.isSuccess) {
            throw BadRequestException("Class already exist")
        } else {
            if (collection.insertOne(classs).wasAcknowledged()) {
                classs.toResult()
            } else {
                throw InternalServerException("Create class failed")
            }
        }
    }

    override suspend fun getClasssByName(name: String): Result<Classs> {
        return collection.findOne(Classs::name eq name).toResult()
    }

    override suspend fun getClasssById(id: String): Result<Classs> {
        val bsonId: Id<Classs> = ObjectId(id).toId()
        return collection.findOne(Classs::id eq bsonId).toResult()
    }

    override suspend fun getClasss(): Result<List<Classs>> {
        return collection.find().toList().toResult()
    }

}