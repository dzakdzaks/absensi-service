package com.dzakdzaks.absensi.data.classs.model

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Classs(
    @BsonId
    val id: Id<Classs>? = null,
    val name: String?,
    val user: String?,
    val createdAt: String?,
    val updatedAt: String?,
)
