package com.dzakdzaks.absensi.data.absenplace.model

import com.dzakdzaks.absensi.util.getCurrentDateInString

fun AbsenPlace.toAbsenPlaceResponse(): AbsenPlaceResponse =
    AbsenPlaceResponse(
        id = this.id.toString(),
        name = this.name,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )

fun AbsenPlaceRequest.toAbsenPlace(): AbsenPlace =
    AbsenPlace(
        name = this.name,
        createdAt = getCurrentDateInString(),
        updatedAt = getCurrentDateInString(),
    )