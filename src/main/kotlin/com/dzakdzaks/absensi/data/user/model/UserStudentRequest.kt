package com.dzakdzaks.absensi.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class UserStudentRequest(
    val username: String,
    val classs: String
)