package com.example.questions.social

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.mimsoft.social.SocialController

fun Route.routeToSocial(){
    get("/socials") {
        val socials = SocialController.getAll()
        if (socials.isNotEmpty()){
            call.respond(socials)
        }else {
            call.respond(HttpStatusCode.NoContent)
        }
    }

    get("/social") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id != null) {
            val social = SocialController.get(id)
            if (social != null) {
                call.respond(social)
            }else
                call.respond(HttpStatusCode.NoContent)
        }else
            call.respond(HttpStatusCode.BadRequest)
    }

    route("/social") {
        post {
            val social = call.receive<SocialModel>()
            SocialController.add(social)
            call.respond(HttpStatusCode.OK)
        }

        put {
            val social = call.receive<SocialModel>()
            SocialController.edit(social)
            call.respond(HttpStatusCode.OK)
        }

        delete {
            val social = call.receive<SocialModel>()
            SocialController.delete(social)
            call.respond(HttpStatusCode.OK)
        }
    }
}