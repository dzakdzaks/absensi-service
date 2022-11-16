package com.dzakdzaks.absensi.data.user.model

import com.dzakdzaks.absensi.data.classs.model.ClassResponse
import com.dzakdzaks.absensi.data.role.model.RoleResponse
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: String? = null,
    val name: String?,
    val username: String?,
    val role: RoleResponse?,
    val classs: ClassResponse?,
    val createdAt: String?,
    val updatedAt: String?,
)