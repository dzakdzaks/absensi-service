package com.dzakdzaks.absensi.data.role.model

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Role(
    @BsonId
    val id: Id<Role>? = null,
    val name: String?,
    val createdAt: String?,
    val updatedAt: String?,
)
