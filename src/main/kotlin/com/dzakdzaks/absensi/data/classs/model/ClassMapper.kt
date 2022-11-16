@file:JvmName("ClasssKt")

package com.dzakdzaks.absensi.data.classs.model

import com.dzakdzaks.absensi.data.user.model.UserResponse
import com.dzakdzaks.absensi.util.getCurrentDateInString

fun Classs.toClasssResponse(
    userResponse: UserResponse? = null
): ClassResponse =
    ClassResponse(
        id = this.id.toString(),
        name = this.name,
        user = userResponse,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )

fun ClassRequest.toClasss(): Classs =
    Classs(
        name = this.name,
        user = this.user,
        createdAt = getCurrentDateInString(),
        updatedAt = getCurrentDateInString(),
    )