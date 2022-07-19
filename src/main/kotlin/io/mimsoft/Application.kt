package io.mimsoft

import com.example.utils.plugins.configureRouting
import com.example.utils.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.mimsoft.utils.plugins.configureMonitoring

fun main() {


    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureMonitoring()
        configureSerialization()
    }.start(wait = true)
}