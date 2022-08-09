package io.mimsoft.utils.plugins

import com.example.questions.routeToQuestions
import com.example.questions.social.routeToSocial
import io.mimsoft.staff.routeToStaff
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.mimsoft.about.routeToAbout
import io.mimsoft.admin.routeToAdmin
import io.mimsoft.advantege.routToAdventure
import io.mimsoft.contact.routeToContact
import io.mimsoft.data.routeToData
import io.mimsoft.feedback.routeToFeedback
import io.mimsoft.file.routeToFile
import io.mimsoft.header.routeToHeader
import io.mimsoft.portfolio.routeToPortfolio
import io.mimsoft.service.routeToService
import io.mimsoft.session.routeToSession

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello DreamArch BACK")
        }

        route("/api/v1"){
            get("/") {
                call.respondText("Hello DreamArch BACK")
            }

            routeToStaff()
            routeToQuestions()
            routeToSocial()
            routeToAbout()
            routeToAdmin()
            routeToContact()
            routeToService()
            routeToFeedback()
            routeToFile()
            routeToPortfolio()
            routeToHeader()
            routeToSession()
            routeToData()

            routToAdventure()
        }


    }
}
