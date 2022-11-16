package com.dzakdzaks.absensi.data.classs.model

import kotlinx.serialization.Serializable

@Serializable
data class ClassRequest(
    val name: String,
    val user: String?,
)
