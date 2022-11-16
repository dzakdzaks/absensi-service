package com.dzakdzaks.absensi.data.absentime.model

import kotlinx.serialization.Serializable

@Serializable
data class AbsenTimeRequest(
    val name: String,
    val absenPlace: String,
)
