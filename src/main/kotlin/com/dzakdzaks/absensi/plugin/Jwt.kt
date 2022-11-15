package com.dzakdzaks.absensi.plugin

import com.dzakdzaks.absensi.service.UserService
import com.dzakdzaks.absensi.util.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.ktor.ext.inject


fun Application.configureJwt() {

    val service by inject<UserService>()

    install(Authentication) {
        jwt {
            verifier(JwtConfig.verifier)
            realm = "com.dzakdzaks"
            validate {
                it.payload.getClaim("id").asString()?.let { id ->
                    service.getUserByIdPrincipal(id).getOrNull()
                }
            }
        }
    }
}