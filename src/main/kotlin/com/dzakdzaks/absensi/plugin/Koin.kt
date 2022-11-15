package com.dzakdzaks.absensi.plugin

import com.dzakdzaks.absensi.di.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(appModule, repositoryModule, serviceModule)
    }
}