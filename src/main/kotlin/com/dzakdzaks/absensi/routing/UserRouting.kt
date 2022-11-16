package com.dzakdzaks.absensi.routing

import com.dzakdzaks.absensi.data.user.model.*
import com.dzakdzaks.absensi.service.UserService
import com.dzakdzaks.absensi.util.toResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.authRouting() {

    val service by inject<UserService>()

    routing {

        route("/user") {

            /** Login */
            post("/login") {
                val userLoginRequest = call.receive<UserLoginRequest>()
                call.respond(
                    HttpStatusCode.OK,
                    service.login(userLoginRequest).toResponse("Success login user")
                )
            }

            /** Register */
            post("/register") {
                val request = call.receive<UserRegisterRequest>()
                val user = request.toUserTeacher()

                call.respond(
                    HttpStatusCode.Created,
                    service.register(
                        user = user,
                    ).toResponse("Success create user")
                )
            }

            authenticate {

                /** Create Student */
                post("/create-student") {
                    val request = call.receive<UserStudentRequest>()
                    val student = request.toUserStudent()

                    call.respond(
                        HttpStatusCode.Created,
                        service.createUser(
                            user = student
                        ).toResponse("Success create student")
                    )
                }

                /** Get All User */
                get("/all") {
                    val role = call.request.queryParameters["role"]
                    val classs = call.request.queryParameters["classs"]
                    call.respond(
                        HttpStatusCode.OK,
                        service.getUsers(role, classs).toResponse("All user")
                    )
                }

                /** Get User By ID */
                get("{id}") {
                    val id = call.parameters["id"]
                    call.respond(
                        HttpStatusCode.OK,
                        service.getUserById(id).toResponse("User by id")
                    )
                }
            }

        }
    }
}