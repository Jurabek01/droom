package io.mimsoft.advantege

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.routToAdventure() {

    get("/advantages") {
        val serviceId = call.parameters["service"]?.toIntOrNull()
        val advantages = AdvantageController.getAll(
            serviceId = serviceId
        )

        if (advantages.isNotEmpty())
            call.respond(advantages)
        else
            call.respond(HttpStatusCode.NoContent)
    }

    get("/advantage") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id != null) {
            val advantage = AdvantageController.get(id)
            if (advantage != null)
                call.respond(advantage)
            else
                call.respond(HttpStatusCode.NoContent)
        } else
            call.respond(HttpStatusCode.BadRequest)

    }

    authenticate("admin") {

        route("/advantage") {

            post {
                val advantage = call.receive<AdvantageModel>()
                AdvantageController.add(advantage)
                call.respond(HttpStatusCode.OK)
            }

            put {
                val advantage = call.receive<AdvantageModel>()
                AdvantageController.edit(advantage)
                call.respond(HttpStatusCode.OK)
            }

            delete {
                val advantage = call.receive<AdvantageModel>()
                AdvantageController.delete(advantage)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}