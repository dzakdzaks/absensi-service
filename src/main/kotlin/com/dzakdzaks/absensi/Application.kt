package com.dzakdzaks.absensi

import com.dzakdzaks.absensi.plugin.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    configureStatusPage()
    configureKoin()
    configureJwt()
    configureSerialization()
    configureRouting()
}
