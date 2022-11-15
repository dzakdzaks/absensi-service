package com.dzakdzaks.absensi.data.user.model

import com.dzakdzaks.absensi.data.classs.model.ClasssResponse
import com.dzakdzaks.absensi.data.role.model.RoleResponse
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: String? = null,
    val username: String,
    val role: RoleResponse?,
    val classs: ClasssResponse?
)