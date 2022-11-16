package com.dzakdzaks.absensi.service

import com.dzakdzaks.absensi.data.classs.ClasssRepository
import com.dzakdzaks.absensi.data.classs.model.Classs
import com.dzakdzaks.absensi.data.classs.model.ClassResponse
import com.dzakdzaks.absensi.data.classs.model.toClasssResponse
import com.dzakdzaks.absensi.data.role.RoleRepository
import com.dzakdzaks.absensi.data.role.model.RoleResponse
import com.dzakdzaks.absensi.data.role.model.toRoleResponse
import com.dzakdzaks.absensi.data.user.UserRepository
import com.dzakdzaks.absensi.data.user.model.UserResponse
import com.dzakdzaks.absensi.data.user.model.toUserResponse
import io.ktor.server.plugins.*
import org.bson.types.ObjectId

class ClasssService(
    private val classRepository: ClasssRepository,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,

    ) {

    private suspend fun roleResponse(id: String?): RoleResponse? = id?.let { idNonNull ->
        roleRepository.getRoleById(idNonNull).map { it.toRoleResponse() }.getOrNull()
    }

    private suspend fun userResponse(id: String?): UserResponse? = id?.let { idNonNull ->
        userRepository.getUserById(idNonNull).map { it.toUserResponse(
            roleResponse(it.role)
        ) }.getOrNull()
    }

    suspend fun createClasss(classs: Classs): Result<ClassResponse> {
        return classRepository.createClasss(classs).map { it.toClasssResponse(userResponse(it.user)) }
    }

    suspend fun getClassByName(name: String): Result<ClassResponse> =
        classRepository.getClasssByName(name).map { it.toClasssResponse(userResponse(it.user)) }

    suspend fun getClassById(id: String?): Result<ClassResponse> {
        if (id.isNullOrEmpty()) throw BadRequestException("Id not found")
        if (ObjectId.isValid(id).not()) throw BadRequestException("Wrong format id")
        return classRepository.getClasssById(id).map { it.toClasssResponse(userResponse(it.user)) }
    }

    suspend fun getClasses(): Result<List<ClassResponse>> = classRepository.getClasss().map {
        it.map { item -> item.toClasssResponse(userResponse(item.user)) }
    }

}