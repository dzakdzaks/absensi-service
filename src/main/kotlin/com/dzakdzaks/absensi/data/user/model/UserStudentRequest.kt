package com.dzakdzaks.absensi.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class UserStudentRequest(
    val name: String,
    val classs: String
)