package com.dzakdzaks.absensi.data.absenplace

import com.dzakdzaks.absensi.data.absenplace.model.AbsenPlace
import com.dzakdzaks.absensi.data.base.InternalServerException
import com.dzakdzaks.absensi.util.toResult
import io.ktor.server.plugins.*
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

interface AbsenPlaceRepository {
    suspend fun createAbsenPlace(absenPlace: AbsenPlace): Result<AbsenPlace>
    suspend fun getAbsenPlaces(): Result<List<AbsenPlace>>
    suspend fun getAbsenPlaceByName(name: String): Result<AbsenPlace>
    suspend fun getAbsenPlaceById(id: String): Result<AbsenPlace>
}

class AbsenPlaceRepositoryImpl(
    coroutineDatabase: CoroutineDatabase
) : AbsenPlaceRepository {

    private val collection = coroutineDatabase.getCollection<AbsenPlace>()

    override suspend fun createAbsenPlace(absenPlace: AbsenPlace): Result<AbsenPlace> {
        if (absenPlace.name.isNullOrEmpty()) throw BadRequestException("Name is empty")
        val isClassExist = getAbsenPlaceByName(absenPlace.name)
        return if (isClassExist.isSuccess) {
            throw BadRequestException("AbsenPlace already exist")
        } else {
            if (collection.insertOne(absenPlace).wasAcknowledged()) {
                absenPlace.toResult()
            } else {
                throw InternalServerException("Create class failed")
            }
        }
    }

    override suspend fun getAbsenPlaceByName(name: String): Result<AbsenPlace> {
        return collection.findOne(AbsenPlace::name eq name).toResult()
    }

    override suspend fun getAbsenPlaceById(id: String): Result<AbsenPlace> {
        val bsonId: Id<AbsenPlace> = ObjectId(id).toId()
        return collection.findOne(AbsenPlace::id eq bsonId).toResult()
    }

    override suspend fun getAbsenPlaces(): Result<List<AbsenPlace>> {
        return collection.find().toList().toResult()
    }

}