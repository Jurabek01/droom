package io.mimsoft

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.mimsoft.utils.plugins.*

fun main() {


    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureMonitoring()
        configureHTTP()
        configureSerialization()
    }.start(wait = true)
}