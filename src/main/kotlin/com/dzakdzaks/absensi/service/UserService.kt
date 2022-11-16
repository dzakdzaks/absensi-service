package com.dzakdzaks.absensi.service

import at.favre.lib.crypto.bcrypt.BCrypt
import com.dzakdzaks.absensi.data.classs.ClasssRepository
import com.dzakdzaks.absensi.data.classs.model.ClassResponse
import com.dzakdzaks.absensi.data.classs.model.toClasssResponse
import com.dzakdzaks.absensi.data.role.RoleRepository
import com.dzakdzaks.absensi.data.role.model.RoleResponse
import com.dzakdzaks.absensi.data.role.model.toRoleResponse
import com.dzakdzaks.absensi.data.user.UserRepository
import com.dzakdzaks.absensi.data.user.model.*
import com.dzakdzaks.absensi.util.JwtConfig
import io.ktor.server.plugins.*
import org.bson.types.ObjectId

class UserService(
    private val userRepository: UserRepository,
    private val classRepository: ClasssRepository,
    private val roleRepository: RoleRepository,
) {

    private suspend fun roleResponse(id: String?): RoleResponse? = id?.let { idNonNull ->
        roleRepository.getRoleById(idNonNull).map { it.toRoleResponse() }.getOrNull()
    }

    private suspend fun classResponse(id: String?): ClassResponse? = id?.let { idNonNull ->
        classRepository.getClasssById(idNonNull).map { it.toClasssResponse() }.getOrNull()
    }

    suspend fun register(
        user: User
    ): Result<UserLoginResponse> {
        return userRepository.createUser(user).map {
            val token = JwtConfig.generateToken(it)
            it.toUserLoginResponse(
                token = token,
                roleResponse = roleResponse(it.role),
                classResponse = classResponse(it.classs)
            )
        }
    }

    suspend fun login(
        userLoginRequest: UserLoginRequest
    ): Result<UserLoginResponse> {
        val resulUser = userRepository.getUserByUsername(userLoginRequest.username)
        return resulUser.map {
            if (it.role == "6374e0435447b54d1f0054c1" || it.role == "6374e0a05447b54d1f0054c2") {
                val resultCheckPassword = BCrypt.verifyer().verify(userLoginRequest.password.toCharArray(), it.password)
                if (resultCheckPassword.verified) {
                    val token = JwtConfig.generateToken(it)
                    it.toUserLoginResponse(
                        token = token,
                        roleResponse = roleResponse(it.role),
                        classResponse = classResponse(it.classs)
                    )
                } else {
                    throw BadRequestException("Wrong password")
                }
            } else {
                throw BadRequestException("User must be admin or teacher")
            }
        }
    }

    suspend fun createUser(
        user: User
    ): Result<UserResponse> {
        return userRepository.createUser(user).map {
            it.toUserResponse(
                roleResponse = roleResponse(it.role),
                classResponse = classResponse(it.classs)
            )
        }
    }


    suspend fun getUserByIdPrincipal(id: String): Result<User> = userRepository.getUserById(id)

    suspend fun getUserById(id: String?): Result<UserResponse> {
        if (id.isNullOrEmpty()) throw BadRequestException("Id not found")
        if (ObjectId.isValid(id).not()) throw BadRequestException("Wrong format id")
        return userRepository.getUserById(id).map { user ->
            user.toUserResponse(
                roleResponse = roleResponse(user.role),
                classResponse = classResponse(user.classs)
            )
        }
    }

    suspend fun getUsers(role: String?, classs: String?): Result<List<UserResponse>> =
        userRepository.getUsers(role, classs).map {
            it.map { item ->
                item.toUserResponse(
                    roleResponse = roleResponse(item.role),
                    classResponse = classResponse(item.classs)
                )
            }
        }
}