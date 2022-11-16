package com.dzakdzaks.absensi.data.user.model

import com.dzakdzaks.absensi.data.classs.model.ClasssResponse
import com.dzakdzaks.absensi.data.role.model.RoleResponse

fun User.toUserResponse(roleResponse: RoleResponse?, classsResponse: ClasssResponse? = null): UserResponse =
    UserResponse(
        id = this.id.toString(),
        username = this.username,
        role = roleResponse,
        classs = classsResponse,
    )

fun User.toUserLoginResponse(token: String, roleResponse: RoleResponse?, classsResponse: ClasssResponse? = null): UserLoginResponse =
    UserLoginResponse(
        id = this.id.toString(),
        username = this.username,
        token = token,
        role = roleResponse,
        classs = classsResponse,
    )

/** Teacher role 637355670b4f6b21487b5838 */
fun UserRegisterRequest.toUserTeacher(): User =
    User(
        username = this.username,
        password = this.password,
        role = "637355670b4f6b21487b5838"
    )

/** Student role 63735d72fcb7c9549e89e7e5 */
fun UserStudentRequest.toUserStudent(): User =
    User(
        username = this.username,
        password = "student"/*getRandomString(6)*/,
        role = "63735d72fcb7c9549e89e7e5",
        classs = this.classs
    )