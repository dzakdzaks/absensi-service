package com.dzakdzaks.absensi.service

import com.dzakdzaks.absensi.data.classs.ClasssRepository
import com.dzakdzaks.absensi.data.classs.model.Classs
import com.dzakdzaks.absensi.data.classs.model.ClasssResponse
import com.dzakdzaks.absensi.data.classs.model.toClasssResponse
import io.ktor.server.plugins.*
import org.bson.types.ObjectId

class ClasssService(
    private val classRepository: ClasssRepository
) {

    suspend fun createClasss(classs: Classs): Result<ClasssResponse> {
        return classRepository.createClasss(classs).map { it.toClasssResponse() }
    }

    suspend fun getClassByName(name: String): Result<ClasssResponse> =
        classRepository.getClasssByName(name).map { it.toClasssResponse() }

    suspend fun getClassById(id: String): Result<ClasssResponse> {
        if (ObjectId.isValid(id).not()) throw BadRequestException("Wrong format id")
        return classRepository.getClasssById(id).map { it.toClasssResponse() }
    }

    suspend fun getClasses(): Result<List<ClasssResponse>> = classRepository.getClasss().map {
        it.map { item -> item.toClasssResponse() }
    }

}