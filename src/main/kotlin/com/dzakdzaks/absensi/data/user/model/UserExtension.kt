package com.dzakdzaks.absensi.data.user.model

import com.dzakdzaks.absensi.data.classs.model.ClassResponse
import com.dzakdzaks.absensi.data.role.model.RoleResponse
import com.dzakdzaks.absensi.util.getCurrentDateInString
import com.dzakdzaks.absensi.util.getInitialName

fun User.toUserResponse(roleResponse: RoleResponse?, classResponse: ClassResponse? = null): UserResponse =
    UserResponse(
        id = this.id.toString(),
        name = this.name,
        username = this.username,
        role = roleResponse,
        classs = classResponse,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )

fun User.toUserLoginResponse(
    token: String,
    roleResponse: RoleResponse?,
    classResponse: ClassResponse? = null
): UserLoginResponse =
    UserLoginResponse(
        id = this.id.toString(),
        name = this.name,
        username = this.username,
        token = token,
        role = roleResponse,
        classs = classResponse,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )

/** Teacher role 6374e0a05447b54d1f0054c2 */
fun UserRegisterRequest.toUserTeacher(): User =
    User(
        name = this.username,
        username = this.username,
        password = this.password,
        role = "6374e0a05447b54d1f0054c2",
        createdAt = getCurrentDateInString(),
        updatedAt = getCurrentDateInString(),
    )

/** Student role 6374e0a45447b54d1f0054c3 */
fun UserStudentRequest.toUserStudent(): User =
    User(
        name = this.name,
        username = this.name.getInitialName().lowercase(),
        password = "student",/*getRandomString(6)*/
        role = "6374e0a45447b54d1f0054c3",
        classs = this.classs,
        createdAt = getCurrentDateInString(),
        updatedAt = getCurrentDateInString(),
    )