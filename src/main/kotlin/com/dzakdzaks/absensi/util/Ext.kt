package com.dzakdzaks.absensi.util

import java.time.LocalDateTime

/** yyyy-MM-dd'T'HH:mm:ss.SSS*/
fun getCurrentDateInString(): String = LocalDateTime.now().toString()

fun String.getInitialName(): String {
    return when {
        isNullOrEmpty() -> {
            "JD"
        }
        contains(" ") -> {
            replace("^\\s*([a-zA-Z]).*\\s+([a-zA-Z])\\S+$".toRegex(), "$1$2").uppercase().take(2)
        }
        length > 1 -> {
            substring(0, 2)
        }
        else -> {
            substring(0, 1)
        }
    }.uppercase()
}