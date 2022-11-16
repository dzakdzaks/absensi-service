package com.dzakdzaks.absensi.data.classs.model

import com.dzakdzaks.absensi.data.user.model.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class ClassResponse(
    val id: String? = null,
    val name: String?,
    val user: UserResponse?,
    val createdAt: String?,
    val updatedAt: String?,
)
