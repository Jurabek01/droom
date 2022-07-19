package io.mimsoft.feedback

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.routeToFeedback() {

    get("/feedbacks") {
        val feedbacks = FeedbackController.getAll()

        if (feedbacks.isNotEmpty()) {
            call.respond(feedbacks)
        }else{
            call.respond(HttpStatusCode.NoContent)
        }
    }

    get("/feedback") {
        val id = call.parameters["id"]?.toIntOrNull()

        if (id != null) {
            val feedback = FeedbackController.get(id)

            if (feedback != null)
                call.respond(feedback)
            else
                call.respond(HttpStatusCode.NoContent)
        }else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    route("/feedback") {
        post {
            val feedback = call.receive<FeedbackModel>()
            FeedbackController.add(feedback)
            call.respond(HttpStatusCode.OK)
        }

        put {
            val feedback = call.receive<FeedbackModel>()
            FeedbackController.edit(feedback)
            call.respond(HttpStatusCode.OK)
        }

        delete {
            val feedback = call.receive<FeedbackModel>()
            FeedbackController.delete(feedback)
            call.respond(HttpStatusCode.OK)
        }
    }
}