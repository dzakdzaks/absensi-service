package com.dzakdzaks.absensi.data.absentime.model

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class AbsenTime(
    @BsonId
    val id: Id<AbsenTime>? = null,
    val name: String?,
    val absenPlace: String?,
    val createdAt: String?,
    val updatedAt: String?,
)