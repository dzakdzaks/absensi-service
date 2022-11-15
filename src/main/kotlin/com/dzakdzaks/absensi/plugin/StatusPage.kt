package com.dzakdzaks.absensi.plugin

import com.dzakdzaks.absensi.data.base.BaseResponse
import com.dzakdzaks.absensi.data.base.InternalServerException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPage() {
    install(StatusPages) {
        exception<InternalServerException> { call, cause->
            call.respond(
                HttpStatusCode.InternalServerError,
                BaseResponse<String>(
                    status = false,
                    message = cause.message.orEmpty(),
                    data = null
                )
            )
        }

        exception<BadRequestException> { call, cause->
            call.respond(
                HttpStatusCode.BadRequest,
                BaseResponse<String>(
                    status = false,
                    message = cause.message.orEmpty(),
                    data = null
                )
            )
        }

        exception<NotFoundException> { call, cause->
            call.respond(
                HttpStatusCode.NotFound,
                BaseResponse<String>(
                    status = false,
                    message = cause.message.orEmpty(),
                    data = null
                )
            )
        }

        status(HttpStatusCode.Unauthorized) { call, _ ->
            call.respond(
                HttpStatusCode.Unauthorized,
                BaseResponse<String>(
                    status = false,
                    message = "Unauthorized",
                    data = null
                )
            )
        }
    }
}