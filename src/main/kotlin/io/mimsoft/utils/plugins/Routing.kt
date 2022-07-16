package com.example.utils.plugins

import com.example.questions.routeToQuestions
import com.example.questions.social.routeToSocial
import com.example.staff.routeToStaff
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello DreamArch BACK")
        }

        route("/api/v1"){
            routeToStaff()
            routeToQuestions()
            routeToSocial()
        }


    }
}
