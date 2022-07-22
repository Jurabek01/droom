package io.mimsoft.file

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.InternalCoroutinesApi
import io.mimsoft.file.FileController.IMAGE
import io.mimsoft.file.FileController.VIDEO


fun Route.routeToFile() {

    authenticate("admin") {


        post("/upload/image") {
            val dir = call.parameters["dir"]
            if (dir.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest)
            } else {
                val result = FileController.uploadFile(
                    multipart = call.receiveMultipart(),
                    dir = dir,
                    type = IMAGE
                )
                if (result.isNotEmpty())
                    call.respond(HttpStatusCode.Created, result)
                else call.respond(HttpStatusCode.Gone)
            }
        }

        delete("/delete/image") {
            val url = call.parameters["url"].toString()
            FileController.deleteFile(
                url = url
            )

            call.respond(HttpStatusCode.OK)
        }

        post("/upload/video") {
            val dir = call.parameters["dir"]
            if (dir.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest)
            } else {
                val result = FileController.uploadFile(
                    multipart = call.receiveMultipart(),
                    dir = dir,
                    type = VIDEO
                )
                if (result.isNotEmpty())
                    call.respond(HttpStatusCode.Created, result)
                else call.respond(HttpStatusCode.Gone)
            }
        }

        delete("/delete/video") {
            val url = call.parameters["url"].toString()
            FileController.deleteFile(
                url = url
            )
            call.respond(HttpStatusCode.OK)
        }
    }

}
