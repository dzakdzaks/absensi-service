package com.dzakdzaks.absensi.service

import com.dzakdzaks.absensi.data.role.RoleRepository
import com.dzakdzaks.absensi.data.role.model.Role
import com.dzakdzaks.absensi.data.role.model.RoleResponse
import com.dzakdzaks.absensi.data.role.model.toRoleResponse
import io.ktor.server.plugins.*
import org.bson.types.ObjectId

class RoleService(
    private val roleRepository: RoleRepository
) {

    suspend fun createRole(role: Role): Result<RoleResponse> {
        return roleRepository.createRole(role).map { it.toRoleResponse() }
    }

    suspend fun getRoleByName(name: String): Result<RoleResponse> =
        roleRepository.getRoleByName(name).map { it.toRoleResponse() }

    suspend fun getRoleById(id: String): Result<RoleResponse> {
        if (ObjectId.isValid(id).not()) throw BadRequestException("Wrong format id")
        return roleRepository.getRoleById(id).map { it.toRoleResponse() }
    }

    suspend fun getRoles(): Result<List<RoleResponse>> = roleRepository.getRoles().map {
        it.map { item -> item.toRoleResponse() }
    }

}