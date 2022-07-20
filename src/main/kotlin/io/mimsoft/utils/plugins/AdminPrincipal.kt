package io.mimsoft.utils.plugins

import io.ktor.server.auth.*

data class AdminPrincipal(
    val id: Int? = null,
    val uuid: String? = null
) : Principal
