package com.dzakdzaks.absensi.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterRequest(
    val username: String,
    val password: String
)