package com.example.questions

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.mimsoft.questions.QuestionController

fun Route.routeToQuestions(){

    get("/questions") {
        val questions = QuestionController.getAll()
        if (questions.isNotEmpty()){
            call.respond(questions)
        }else {
            call.respond(HttpStatusCode.NoContent)
        }
    }

    get("/question") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id != null) {
            val questions = QuestionController.get(id)
            if (questions != null)
                call.respond(questions)
            else
                call.respond(HttpStatusCode.NoContent)
        }else
            call.respond(HttpStatusCode.BadRequest)
    }

    authenticate("admin") {


        route("/question") {
            post {
                val question = call.receive<QuestionsModel>()
                QuestionController.add(question)
                call.respond(HttpStatusCode.OK)
            }

            put {
                val question = call.receive<QuestionsModel>()
                QuestionController.edit(question)
                call.respond(HttpStatusCode.OK)
            }

            delete {
                val question = call.receive<QuestionsModel>()
                QuestionController.delete(question)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}