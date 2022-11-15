package com.dzakdzaks.absensi.util

import com.dzakdzaks.absensi.data.user.model.User
import io.ktor.server.application.*
import io.ktor.server.auth.*

const val BASE_URL =
    "mongodb+srv://dzakdzaks:dzakdzaks@firstcluster.wlsr7.mongodb.net/orderid_db?retryWrites=true&w=majority"
const val DB_NAME_ABSENSI = "absensi"

val ApplicationCall.user get() = authentication.principal<User>()


fun getRandomString(length: Int) : String {
    val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { charset.random() }
        .joinToString("")
}