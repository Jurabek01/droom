package io.mimsoft.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

import java.util.*

object JWTConfig {

    const val issuer = "uz.mimsoft.dreamarch"

    private const val secretAdmin = "lXJ0BVa3M9isxZHZMiSD8"


    private val algorithmAdmin = Algorithm.HMAC512(secretAdmin)

    val verifierAdmin: JWTVerifier = JWT.require(algorithmAdmin).withIssuer(issuer).build()

    fun makeTokenAdmin(id: Int?, username: String?, uuid: String?): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("id", id)
        .withClaim("username", username)
        .withClaim("uuid", uuid)
        .withExpiresAt(getExpiration())
        .sign(algorithmAdmin)

    private fun getExpiration() = Date(System.currentTimeMillis() + 864_000_000_000)

}