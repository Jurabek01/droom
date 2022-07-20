package io.mimsoft

import com.example.utils.configureSerialization
import io.mimsoft.utils.plugins.configureRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.mimsoft.utils.plugins.configureHTTP
import io.mimsoft.utils.plugins.configureMonitoring

fun main() {


    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureMonitoring()
        configureHTTP()
        configureSerialization()
    }.start(wait = true)
}