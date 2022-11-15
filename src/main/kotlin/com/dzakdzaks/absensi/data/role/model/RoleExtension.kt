package com.dzakdzaks.absensi.data.role.model

fun Role.toRoleResponse(): RoleResponse =
    RoleResponse(
        id = this.id.toString(),
        name = this.name
    )

fun RoleRequest.toRole(): Role =
    Role(
        name = this.name,
    )