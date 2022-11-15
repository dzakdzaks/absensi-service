package com.dzakdzaks.absensi.util

import com.dzakdzaks.absensi.data.base.BaseResponse
import com.dzakdzaks.absensi.data.base.InternalServerException
import io.ktor.server.plugins.*

inline fun <reified T> T?.orThrow(
    message: String = "${T::class.simpleName} is not found"
): T {
    return this ?: throw InternalServerException(message)
}

inline fun <reified T> T?.toResult(
    message: String = "${T::class.simpleName} is not found"
): Result<T> {
    return if (this != null) {
        Result.success(this)
    } else {
        Result.failure(Exception(message))
    }
}

fun <T> Result<T>.toResponse(message: String = "Success"): BaseResponse<T> {
    return if (this.isFailure) {
        if ((this.exceptionOrNull()?.message ?: "").contains("is not found")) {
            throw NotFoundException(this.exceptionOrNull()?.message)
        }
        throw InternalServerException(this.exceptionOrNull()?.message ?: "There is something error from response")
    } else {
        BaseResponse.success(this.getOrNull(), message)
    }
}