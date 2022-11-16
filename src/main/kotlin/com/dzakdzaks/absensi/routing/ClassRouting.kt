package com.dzakdzaks.absensi.routing

import com.dzakdzaks.absensi.data.classs.model.ClassRequest
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
                    val request = call.receive<ClassRequest>()
                    call.respond(
                        HttpStatusCode.Created,
                        service.createClasss(request.toClasss()).toResponse("Class created")
                    )
                }

                /** Get All Class */
                get("/all") {
                    call.respond(
                        HttpStatusCode.OK, service.getClasses().toResponse("All class")
                    )
                }

                /** Get Class By ID */
                get("{id}") {
                    val id = call.parameters["id"]
                    call.respond(
                        HttpStatusCode.OK,
                        service.getClassById(id).toResponse("Class by id")
                    )
                }

            }

        }

    }

}