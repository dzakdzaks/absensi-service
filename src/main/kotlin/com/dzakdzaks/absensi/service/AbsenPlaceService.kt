package com.dzakdzaks.absensi.service

import com.dzakdzaks.absensi.data.absenplace.AbsenPlaceRepository
import com.dzakdzaks.absensi.data.absenplace.model.AbsenPlace
import com.dzakdzaks.absensi.data.absenplace.model.AbsenPlaceResponse
import com.dzakdzaks.absensi.data.absenplace.model.toAbsenPlaceResponse
import io.ktor.server.plugins.*
import org.bson.types.ObjectId

class AbsenPlaceService(
    private val absenPlaceRepository: AbsenPlaceRepository
) {

    suspend fun createAbsenPlace(absenPlace: AbsenPlace): Result<AbsenPlaceResponse> {
        return absenPlaceRepository.createAbsenPlace(absenPlace).map { it.toAbsenPlaceResponse() }
    }

    suspend fun getAbsenPlaceByName(name: String): Result<AbsenPlaceResponse> =
        absenPlaceRepository.getAbsenPlaceByName(name).map { it.toAbsenPlaceResponse() }

    suspend fun getAbsenPlaceById(id: String): Result<AbsenPlaceResponse> {
        if (ObjectId.isValid(id).not()) throw BadRequestException("Wrong format id")
        return absenPlaceRepository.getAbsenPlaceById(id).map { it.toAbsenPlaceResponse() }
    }

    suspend fun getAbsenPlaces(): Result<List<AbsenPlaceResponse>> = absenPlaceRepository.getAbsenPlaces().map {
        it.map { item -> item.toAbsenPlaceResponse() }
    }

}