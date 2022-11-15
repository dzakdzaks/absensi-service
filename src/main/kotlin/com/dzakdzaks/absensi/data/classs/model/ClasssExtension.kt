@file:JvmName("ClasssKt")

package com.dzakdzaks.absensi.data.classs.model

fun Classs.toClasssResponse(): ClasssResponse =
    ClasssResponse(
        id = this.id.toString(),
        name = this.name
    )

fun ClasssRequest.toClasss(): Classs =
    Classs(
        name = this.name,
    )