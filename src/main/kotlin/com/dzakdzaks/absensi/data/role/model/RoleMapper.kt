package com.dzakdzaks.absensi.data.role.model

import com.dzakdzaks.absensi.util.getCurrentDateInString

fun Role.toRoleResponse(): RoleResponse =
    RoleResponse(
        id = this.id.toString(),
        name = this.name,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )

fun RoleRequest.toRole(): Role =
    Role(
        name = this.name,
        createdAt = getCurrentDateInString(),
        updatedAt = getCurrentDateInString(),
    )