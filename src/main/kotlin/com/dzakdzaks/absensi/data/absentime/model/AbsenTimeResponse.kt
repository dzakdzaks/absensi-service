package com.dzakdzaks.absensi.data.absentime.model

import com.dzakdzaks.absensi.data.absenplace.model.AbsenPlaceResponse
import kotlinx.serialization.Serializable

@Serializable
data class AbsenTimeResponse(
    val id: String? = null,
    val name: String?,
    val absenPlace: AbsenPlaceResponse?,
    val createdAt: String?,
    val updatedAt: String?,
)
