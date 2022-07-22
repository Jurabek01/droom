package io.mimsoft.portfolio

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.routeToPortfolio() {

    get("/portfolios") {
        val portfolios = PortfolioController.getAll()

        if (portfolios.isNotEmpty())
            call.respond(portfolios)
        else
            call.respond(HttpStatusCode.NoContent)
    }

    get("/portfolio"){
        val id = call.parameters["id"]?.toIntOrNull()

        if (id != null) {
            val portfolio = PortfolioController.get(id)

            if (portfolio != null)
                call.respond(portfolio)
            else
                call.respond(HttpStatusCode.NoContent)
        }else
            call.respond(HttpStatusCode.BadRequest)
    }

    authenticate ("admin") {


        route("/portfolio") {
            post {
                val portfolio = call.receive<PortfolioModel>()
                PortfolioController.add(portfolio)
                call.respond(HttpStatusCode.OK)
            }

            put {
                val portfolio = call.receive<PortfolioModel>()
                PortfolioController.edit(portfolio)
                call.respond(HttpStatusCode.OK)
            }

            delete {
                val portfolio = call.receive<PortfolioModel>()
                PortfolioController.delete(portfolio)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}