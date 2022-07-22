package io.mimsoft.staff

import com.example.staff.StaffController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.routeToStaff() {

    get("/staffs") {
        val staffs = StaffController.getAll()
        if (staffs.isNotEmpty()) {
            call.respond(staffs)
        } else
            call.respond(HttpStatusCode.NoContent)
    }

    get("/staff") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id != null) {
            val staff = StaffController.get(id)
            if (staff != null) {
                call.respond(staff)
            } else
                call.respond(HttpStatusCode.NoContent)
        } else
            call.respond(HttpStatusCode.BadRequest)
    }
    authenticate("admin") {
        route("/staff") {
            // api/v1/staff
            post {
                val staff = call.receive<StaffModel>()
                StaffController.add(staff)
                call.respond(HttpStatusCode.OK)
            }

            put {
                val staff = call.receive<StaffModel>()
                StaffController.edit(staff)
                call.respond(HttpStatusCode.OK)
            }

            delete {
                val staff = call.receive<StaffModel>()
                StaffController.delete(staff)
                call.respond(HttpStatusCode.OK)
            }

        }
    }

}