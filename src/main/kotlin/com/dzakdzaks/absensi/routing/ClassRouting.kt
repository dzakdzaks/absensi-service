package com.dzakdzaks.absensi.routing

import com.dzakdzaks.absensi.data.classs.model.ClasssRequest
import com.dzakdzaks.absensi.data.classs.model.toClasss
import com.dzakdzaks.absensi.service.ClasssService
import com.dzakdzaks.absensi.util.toResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.classRouting() {

    val service by inject<ClasssService>()

    routing {

        authenticate {

            route("/class") {

                /** Create Class */
                post {
                    val request = call.receive<ClasssRequest>()
                    call.respond(
                        HttpStatusCode.Created,
                        service.createClasss(request.toClasss()).toResponse("Class created")
                    )
                }

                /** Get All Class */
                get {
                    call.respond(
                        HttpStatusCode.OK, service.getClasses().toResponse("All class")
                    )
                }

            }

        }

    }

}