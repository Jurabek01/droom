package io.mimsoft.service

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.routeToService() {

    get("/services") {
        val service = ServiceController.getAll()
        if (service.isNotEmpty())
            call.respond(service)
        else
            call.respond(HttpStatusCode.NoContent)
    }

    get("/service") {
        val id = call.parameters["id"]?.toIntOrNull()

        if (id != null){
            val service = ServiceController.get(id)

            if (service != null)
                call.respond(service)
            else
                call.respond(HttpStatusCode.NoContent)
        }else
            call.respond(HttpStatusCode.BadRequest)

    }

    authenticate ("admin") {


        route("/service") {

            post {
                val service = call.receive<ServiceModel>()
                ServiceController.add(service)
                call.respond(HttpStatusCode.OK)
            }

            put {
                val service = call.receive<ServiceModel>()
                ServiceController.edit(service)
                call.respond(HttpStatusCode.OK)
            }

            delete {
                val service = call.receive<ServiceModel>()
                ServiceController.delete(service)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}