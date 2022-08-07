package io.mimsoft.adventures

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.routToAdventure() {

    get("/adventures") {
        val serviceId = call.parameters["service"]?.toIntOrNull()
        val adventures = AdventuresController.getAll(
            serviceId = serviceId
        )

        if (adventures.isNotEmpty())
            call.respond(adventures)
        else
            call.respond(HttpStatusCode.NoContent)
    }

    get("/adventure") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id != null) {
            val adventure = AdventuresController.get(id)
            if (adventure != null)
                call.respond(adventure)
            else
                call.respond(HttpStatusCode.NoContent)
        } else
            call.respond(HttpStatusCode.BadRequest)

    }

    authenticate("admin") {

        route("/adventure") {

            post {
                val adventure = call.receive<AdventuresModel>()
                AdventuresController.add(adventure)
                call.respond(HttpStatusCode.OK)
            }

            put {
                val adventure = call.receive<AdventuresModel>()
                AdventuresController.edit(adventure)
                call.respond(HttpStatusCode.OK)
            }

            delete {
                val adventure = call.receive<AdventuresModel>()
                AdventuresController.delete(adventure)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}