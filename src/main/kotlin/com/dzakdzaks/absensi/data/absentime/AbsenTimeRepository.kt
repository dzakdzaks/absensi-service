package com.dzakdzaks.absensi.data.absentime

import com.dzakdzaks.absensi.data.absentime.model.AbsenTime
import com.dzakdzaks.absensi.data.base.InternalServerException
import com.dzakdzaks.absensi.util.toResult
import io.ktor.server.plugins.*
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

interface AbsenTimeRepository {
    suspend fun createAbsenTime(absenTime: AbsenTime): Result<AbsenTime>
    suspend fun getAbsenTimes(absenPlace: String?): Result<List<AbsenTime>>
    suspend fun getAbsenTimeByName(name: String): Result<AbsenTime>
    suspend fun getAbsenTimeById(id: String): Result<AbsenTime>
}

class AbsenTimeRepositoryImpl(
    coroutineDatabase: CoroutineDatabase
) : AbsenTimeRepository {

    private val collection = coroutineDatabase.getCollection<AbsenTime>()

    override suspend fun createAbsenTime(absenTime: AbsenTime): Result<AbsenTime> {
        if (absenTime.name.isNullOrEmpty()) throw BadRequestException("Name is empty")
        val isClassExist = getAbsenTimeByName(absenTime.name)
        return if (isClassExist.isSuccess) {
            throw BadRequestException("AbsenTime already exist")
        } else {
            if (collection.insertOne(absenTime).wasAcknowledged()) {
                absenTime.toResult()
            } else {
                throw InternalServerException("Create AbsenTime failed")
            }
        }
    }

    override suspend fun getAbsenTimeByName(name: String): Result<AbsenTime> {
        return collection.findOne(AbsenTime::name eq name).toResult()
    }

    override suspend fun getAbsenTimeById(id: String): Result<AbsenTime> {
        val bsonId: Id<AbsenTime> = ObjectId(id).toId()
        return collection.findOne(AbsenTime::id eq bsonId).toResult()
    }

    override suspend fun getAbsenTimes(absenPlace: String?): Result<List<AbsenTime>> {
        val filters: MutableList<Bson> = mutableListOf<Bson>().apply {
            absenPlace?.takeIf { add(AbsenTime::absenPlace eq it) }
        }
        return collection.find(*filters.toTypedArray()).toList().toResult()
    }

}