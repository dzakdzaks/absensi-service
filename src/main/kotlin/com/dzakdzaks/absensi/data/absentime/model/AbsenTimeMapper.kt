package com.dzakdzaks.absensi.data.absentime.model

import com.dzakdzaks.absensi.data.absenplace.model.AbsenPlaceResponse
import com.dzakdzaks.absensi.util.getCurrentDateInString

fun AbsenTime.toAbsenTimeResponse(
    absenPlaceResponse: AbsenPlaceResponse?,
): AbsenTimeResponse =
    AbsenTimeResponse(
        id = this.id.toString(),
        name = this.name,
        absenPlace = absenPlaceResponse,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )

fun AbsenTimeRequest.toAbsenTime(): AbsenTime =
    AbsenTime(
        name = this.name,
        absenPlace = this.absenPlace,
        createdAt = getCurrentDateInString(),
        updatedAt = getCurrentDateInString(),
    )