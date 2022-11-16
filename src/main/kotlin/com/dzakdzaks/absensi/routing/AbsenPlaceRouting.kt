package com.dzakdzaks.absensi.routing

import com.dzakdzaks.absensi.data.absenplace.model.AbsenPlaceRequest
import com.dzakdzaks.absensi.data.absenplace.model.toAbsenPlace
import com.dzakdzaks.absensi.service.AbsenPlaceService
import com.dzakdzaks.absensi.util.toResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.absenPlaceRouting() {

    val service by inject<AbsenPlaceService>()

    routing {

        authenticate {

            route("/absen-place") {

                /** Create Absen Place */
                post {
                    val request = call.receive<AbsenPlaceRequest>()
                    call.respond(
                        HttpStatusCode.Created,
                        service.createAbsenPlace(request.toAbsenPlace()).toResponse("Absen Place created")
                    )
                }

                /** Get All Absen Place */
                get {
                    call.respond(
                        HttpStatusCode.OK, service.getAbsenPlaces().toResponse("All Absen Place")
                    )
                }

            }

        }

    }

}