package com.dzakdzaks.absensi.util

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.dzakdzaks.absensi.data.user.model.User
import java.util.*

object JwtConfig {
    private const val secret = "zAP5MBA4B4Ijz0MZaS48" // use your own secret
    private const val issuer = "com.dzakdzaks"  // use your own issuer
    private const val validityInMs = 31_556_952_000 // 1 year
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    /**
     * Produce a token for this combination of name and password
     */
    fun generateToken(user: User): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("id", user.id.toString())
        .withClaim("username", user.username)
        .withExpiresAt(getExpiration())  // optional
        .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}