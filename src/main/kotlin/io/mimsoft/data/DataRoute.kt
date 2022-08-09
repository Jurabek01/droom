package io.mimsoft.data

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.routeToData() {
    get("/data") {
        val data = DataController.get()
        call.respond(data ?: HttpStatusCode.NoContent)
    }
}