package com.dzakdzaks.absensi.data.absenplace.model

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class AbsenPlace(
    @BsonId
    val id: Id<AbsenPlace>? = null,
    val name: String?,
    val createdAt: String?,
    val updatedAt: String?,
)
