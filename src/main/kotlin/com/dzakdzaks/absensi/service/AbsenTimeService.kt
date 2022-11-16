package com.dzakdzaks.absensi.service

import com.dzakdzaks.absensi.data.absenplace.AbsenPlaceRepository
import com.dzakdzaks.absensi.data.absenplace.model.AbsenPlaceResponse
import com.dzakdzaks.absensi.data.absenplace.model.toAbsenPlaceResponse
import com.dzakdzaks.absensi.data.absentime.AbsenTimeRepository
import com.dzakdzaks.absensi.data.absentime.model.AbsenTime
import com.dzakdzaks.absensi.data.absentime.model.AbsenTimeResponse
import com.dzakdzaks.absensi.data.absentime.model.toAbsenTimeResponse
import io.ktor.server.plugins.*
import org.bson.types.ObjectId

class AbsenTimeService(
    private val absenTimeRepository: AbsenTimeRepository,
    private val absenPlaceRepository: AbsenPlaceRepository,
) {

    private suspend fun absenPlace(id: String?): AbsenPlaceResponse? = id?.let { idNonNull ->
        absenPlaceRepository.getAbsenPlaceById(idNonNull).map { it.toAbsenPlaceResponse() }.getOrNull()
    }

    suspend fun createAbsenTime(absenTime: AbsenTime): Result<AbsenTimeResponse> {
        return absenTimeRepository.createAbsenTime(absenTime).map { it.toAbsenTimeResponse(absenPlace(it.absenPlace)) }
    }

    suspend fun getAbsenTimeByName(name: String): Result<AbsenTimeResponse> =
        absenTimeRepository.getAbsenTimeByName(name).map { it.toAbsenTimeResponse(absenPlace(it.absenPlace)) }

    suspend fun getAbsenTimeById(id: String): Result<AbsenTimeResponse> {
        if (ObjectId.isValid(id).not()) throw BadRequestException("Wrong format id")
        return absenTimeRepository.getAbsenTimeById(id).map { it.toAbsenTimeResponse(absenPlace(it.absenPlace)) }
    }

    suspend fun getAbsenTimes(): Result<List<AbsenTimeResponse>> = absenTimeRepository.getAbsenTimes().map {
        it.map { item -> item.toAbsenTimeResponse(absenPlace(item.absenPlace)) }
    }

}