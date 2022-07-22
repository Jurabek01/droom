package io.mimsoft.header

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.routeToHeader() {

    get("/header") {
        val head = HeaderController.get()

        if (head != null)
            call.respond(head)
        else
            call.respond(HttpStatusCode.NoContent)
    }

    authenticate("admin") {
        put("/header") {
            val head = call.receive<HeaderModel>()
            HeaderController.add(head)
            call.respond(HttpStatusCode.OK)
        }
    }
}