package com.dzakdzaks.absensi.routing

import com.dzakdzaks.absensi.data.absentime.model.AbsenTimeRequest
import com.dzakdzaks.absensi.data.absentime.model.toAbsenTime
import com.dzakdzaks.absensi.service.AbsenTimeService
import com.dzakdzaks.absensi.util.toResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.absenTimeRouting() {

    val service by inject<AbsenTimeService>()

    routing {

        authenticate {

            route("/absen-time") {

                /** Create Absen Time */
                post {
                    val request = call.receive<AbsenTimeRequest>()
                    call.respond(
                        HttpStatusCode.Created,
                        service.createAbsenTime(request.toAbsenTime()).toResponse("Absen Time created")
                    )
                }

                /** Get All Absen Time */
                get("/all") {
                    val absenPlace = call.request.queryParameters["absenPlace"]
                    call.respond(
                        HttpStatusCode.OK, service.getAbsenTimes(absenPlace).toResponse("All Absen Time")
                    )
                }

                /** Get AbsenTime By ID */
                get("{id}") {
                    val id = call.parameters["id"]
                    call.respond(
                        HttpStatusCode.OK,
                        service.getAbsenTimeById(id).toResponse("AbsenTime by id")
                    )
                }

            }

        }

    }

}