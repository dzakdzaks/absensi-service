package com.dzakdzaks.absensi.data.absenplace.model

import kotlinx.serialization.Serializable

@Serializable
data class AbsenPlaceResponse(
    val id: String? = null,
    val name: String?,
    val createdAt: String?,
    val updatedAt: String?,
)
