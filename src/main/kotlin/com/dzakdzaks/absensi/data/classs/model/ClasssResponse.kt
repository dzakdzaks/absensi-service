package com.dzakdzaks.absensi.data.classs.model

import kotlinx.serialization.Serializable

@Serializable
data class ClasssResponse(
    val id: String? = null,
    val name: String
)
