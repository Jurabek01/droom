package io.mimsoft.about

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.routeToAbout() {
    get("/about") {
        val about = AboutController.get()

        if (about != null) {
            call.respond(about)
        }
        else
            call.respond(HttpStatusCode.NoContent)
    }

    route("/about"){
        post {
            val about = call.receive<AboutModel>()
            AboutController.add(about)
            call.respond(HttpStatusCode.OK)
        }
    }
}