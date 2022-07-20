package io.mimsoft.admin


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.mimsoft.session.SessionController
import io.mimsoft.session.SessionModel
import io.mimsoft.utils.plugins.AdminPrincipal


fun Route.routeToAdmin() {

    post("/admin/auth") {
        val admin = call.receive<AdminModel>()
        val authAdmin = AdminController.authAdmin(admin)
        if (authAdmin == null) {
            call.respond(HttpStatusCode.BadRequest)
        } else
            call.respond(authAdmin)
    }


    authenticate("admin") {

        post("/admin/logout") {
            val adminPrincipal = call.authentication.principal<AdminPrincipal>()
            if (SessionController.editSession(
                    SessionModel(
                        adminId = adminPrincipal?.id,
                        isExpired = true,
                        sessionUuid = adminPrincipal?.uuid
                    )
                )
            )
                call.respond(HttpStatusCode.OK)
        }

        put("/admin") {
            val adminPrincipal = call.authentication.principal<AdminPrincipal>()
            val admin = call.receive<AdminModel>()
            if (admin.id == adminPrincipal?.id) {
                AdminController.editAdmin(admin)
                call.respond(HttpStatusCode.Accepted)
            } else
                call.respond(HttpStatusCode.NotAcceptable)
        }

        get("/admin") {
            val pr = call.authentication.principal<AdminPrincipal>()
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null && id != pr?.id) {
                call.respond(HttpStatusCode.Conflict)
            } else {
                val admin = AdminController.getAdmin(id = pr?.id)
                if (admin != null)
                    call.respond(admin)
                else
                    call.respond(HttpStatusCode.NoContent)
            }


        }


        post("/admin/password") {
//            val auth = call.authentication.principal<AdminPrincipal>()
//
//            val admin: AdminModel = call.receive()
//
//            if (admin.id != null) {
//                AdminController.changePassword(AdminModel(auth?.id, admin.password))
//                call.respond(HttpStatusCode.Accepted)
//            } else {
//                if (admin.id != auth?.id) {
//                    call.respond(HttpStatusCode.BadRequest)
//                } else {
//                    AdminController.changePassword(AdminModel(auth?.id, admin.password))
//                    call.respond(HttpStatusCode.Accepted)
//                }
//            }
        }

        delete("/admin") {
            val pr = call.authentication.principal<AdminPrincipal>()
            val admin = call.receive<AdminModel>()

            if (pr?.id != admin.id) {
                AdminController.deleteAdmin(admin)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotAcceptable)
            }
        }

        get("/admins") {
            val admins = AdminController.getAdmins()
            call.respond(admins)
        }


    }
}
