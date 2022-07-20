package io.mimsoft.session

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.mimsoft.utils.plugins.AdminPrincipal


fun Route.routeToSession() {


    authenticate("admin") {
        get("/sessions") {
            val adminPr = call.principal<AdminPrincipal>()


            val sessions = SessionController.getSessions()
            if (sessions.isNotEmpty())
                call.respond(sessions)
            else
                call.respond(HttpStatusCode.OK)


        }

        get("/session") {
            val adminPr = call.principal<AdminPrincipal>()

            val session = SessionController.getSession(
                SessionModel(
                    adminId = adminPr?.id
                )
            )
            call.respond(session ?: HttpStatusCode.BadRequest)
        }


        delete("/session") {
            val adminPr = call.principal<AdminPrincipal>()

            val session = call.receive<SessionModel>()
            if (adminPr != null) {
                SessionController.deleteSession(session)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

    }


}
