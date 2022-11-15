package com.dzakdzaks.absensi.data.base

import kotlinx.serialization.Serializable

@Serializable
class BaseResponse<T>(
    val status: Boolean = true,
    val message: String = "success",
    val data: T? = null
) {
    companion object {
        fun <T>success(data: T?, message: String): BaseResponse<T> {
            return BaseResponse(status = true, message = message, data = data)
        }

        fun <T>failure(message: String): BaseResponse<T> {
            return BaseResponse(status = false, message = message)
        }
    }
}