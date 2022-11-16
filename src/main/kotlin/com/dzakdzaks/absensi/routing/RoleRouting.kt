package com.dzakdzaks.absensi.routing

import com.dzakdzaks.absensi.data.role.model.RoleRequest
import com.dzakdzaks.absensi.data.role.model.toRole
import com.dzakdzaks.absensi.service.RoleService
import com.dzakdzaks.absensi.util.toResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.roleRouting() {

    val service by inject<RoleService>()

    routing {

        authenticate {

            route("/role") {

                /** Create Role */
                post {
                    val request = call.receive<RoleRequest>()
                    call.respond(
                        HttpStatusCode.Created,
                        service.createRole(request.toRole()).toResponse("Role created")
                    )
                }

                /** Get All Role */
                get("/all") {
                    call.respond(
                        HttpStatusCode.OK, service.getRoles().toResponse("All role")
                    )
                }

                /** Get Role By ID */
                get("{id}") {
                    val id = call.parameters["id"]
                    call.respond(
                        HttpStatusCode.OK,
                        service.getRoleById(id).toResponse("Role by id")
                    )
                }

            }

        }

    }

}