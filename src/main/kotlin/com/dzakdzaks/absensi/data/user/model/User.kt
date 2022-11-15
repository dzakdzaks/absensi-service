package com.dzakdzaks.absensi.data.user.model

import io.ktor.server.auth.*
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class User(
    @BsonId
    val id: Id<User>? = null,
    val username: String,
    val password: String,
    val role: String? = null,
    val classs: String? = null
) : Principal