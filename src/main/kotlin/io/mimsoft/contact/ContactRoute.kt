package io.mimsoft.contact

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.routeToContact() {

    get("/contact") {
        val contact = ContactController.get()

        if (contact != null) {
            call.respond(contact)
        } else {
            call.respond(HttpStatusCode.NoContent)
        }
    }
    authenticate("admin") {
        put("/contact") {
            val contact = call.receive<ContactModel>()
            ContactController.add(contact)
            call.respond(HttpStatusCode.OK)

        }
    }
}