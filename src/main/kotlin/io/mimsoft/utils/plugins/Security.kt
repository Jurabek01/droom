package io.mimsoft.utils.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.mimsoft.admin.AdminController
import io.mimsoft.session.SessionController
import io.mimsoft.session.SessionModel
import io.mimsoft.utils.AdminPrincipal
import io.mimsoft.utils.JWTConfig


fun Application.configureSecurity() {


    authentication {
        jwt("admin") {

            verifier(JWTConfig.verifierAdmin)
            realm = JWTConfig.issuer
            validate {

                with(it.payload) {
                    val id = getClaim("id").asInt()
                    val username = getClaim("username").asString()
                    val uuid = getClaim("uuid").asString()
                    val sessionModel =
                        SessionController.checkSession(SessionModel(adminId = id, sessionUuid = uuid.toString()))
                    if (sessionModel?.isExpired == false) {
                        val admin = AdminController.checkAdmin(id, username)
                        if (admin != null) AdminPrincipal(
                            id = admin.id,
                            uuid = uuid
                        ) else null
                    } else null
                }
            }
        }
    }
}
