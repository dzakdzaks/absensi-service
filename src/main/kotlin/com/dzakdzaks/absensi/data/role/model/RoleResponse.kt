package com.dzakdzaks.absensi.data.role.model

import kotlinx.serialization.Serializable

@Serializable
data class RoleResponse(
    val id: String? = null,
    val name: String
)
