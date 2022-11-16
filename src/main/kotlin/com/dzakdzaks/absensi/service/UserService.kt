package com.dzakdzaks.absensi.service

import at.favre.lib.crypto.bcrypt.BCrypt
import com.dzakdzaks.absensi.data.classs.ClasssRepository
import com.dzakdzaks.absensi.data.classs.model.ClasssResponse
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

    private suspend fun classResponse(id: String?): ClasssResponse? = id?.let { idNonNull ->
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
                classsResponse = classResponse(it.classs)
            )
        }
    }

    suspend fun login(
        userLoginRequest: UserLoginRequest
    ): Result<UserLoginResponse> {
        val resulUser = userRepository.getUserByUsername(userLoginRequest.username)
        return resulUser.map {
            if (it.role == "637354fc0b4f6b21487b5836" || it.role == "637355670b4f6b21487b5838") {
                val resultCheckPassword = BCrypt.verifyer().verify(userLoginRequest.password.toCharArray(), it.password)
                if (resultCheckPassword.verified) {
                    val token = JwtConfig.generateToken(it)
                    it.toUserLoginResponse(
                        token = token,
                        roleResponse = roleResponse(it.role),
                        classsResponse = classResponse(it.classs)
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
                classsResponse = classResponse(it.classs)
            )
        }
    }


    suspend fun getUserByIdPrincipal(id: String): Result<User> = userRepository.getUserById(id)

    suspend fun getUserById(id: String?): Result<UserResponse> {
        return id?.let {
            if (it.isEmpty()) throw BadRequestException("Id not found")
            if (ObjectId.isValid(it).not()) throw BadRequestException("Wrong format id")
            userRepository.getUserById(id).map { user ->
                user.toUserResponse(
                    roleResponse = roleResponse(user.role),
                    classsResponse = classResponse(user.classs)
                )
            }
        } ?: throw BadRequestException("Id not found")
    }

    suspend fun getUsers(): Result<List<UserResponse>> =
        userRepository.getUsers().map {
            it.map { item ->
                item.toUserResponse(
                    roleResponse = roleResponse(item.role),
                    classsResponse = classResponse(item.classs)
                )
            }
        }

}