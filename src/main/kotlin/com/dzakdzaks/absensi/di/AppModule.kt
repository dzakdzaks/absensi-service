package com.dzakdzaks.absensi.di

import com.dzakdzaks.absensi.data.absenplace.AbsenPlaceRepository
import com.dzakdzaks.absensi.data.absenplace.AbsenPlaceRepositoryImpl
import com.dzakdzaks.absensi.data.classs.ClasssRepository
import com.dzakdzaks.absensi.data.classs.ClasssRepositoryImpl
import com.dzakdzaks.absensi.data.role.RoleRepository
import com.dzakdzaks.absensi.data.role.RoleRepositoryImpl
import com.dzakdzaks.absensi.data.user.UserRepository
import com.dzakdzaks.absensi.data.user.UserRepositoryImpl
import com.dzakdzaks.absensi.service.AbsenPlaceService
import com.dzakdzaks.absensi.service.ClasssService
import com.dzakdzaks.absensi.service.RoleService
import com.dzakdzaks.absensi.service.UserService
import com.dzakdzaks.absensi.util.BASE_URL
import com.dzakdzaks.absensi.util.DB_NAME_ABSENSI
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val appModule = module(createdAtStart = true) {
    factory { KMongo.createClient(BASE_URL).coroutine.getDatabase(DB_NAME_ABSENSI) }
}

val repositoryModule = module {
    single<RoleRepository> { RoleRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<ClasssRepository> { ClasssRepositoryImpl(get()) }
    single<AbsenPlaceRepository> { AbsenPlaceRepositoryImpl(get()) }
}

val serviceModule = module {
    single { UserService(get(), get(), get()) }
    single { RoleService(get()) }
    single { ClasssService(get(), get(), get()) }
    single { AbsenPlaceService(get()) }
}