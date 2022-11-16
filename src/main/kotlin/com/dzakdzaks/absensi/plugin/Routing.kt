package com.dzakdzaks.absensi.plugin

import com.dzakdzaks.absensi.routing.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
    authRouting()
    roleRouting()
    classRouting()
    absenPlaceRouting()
    absenTimeRouting()
}
