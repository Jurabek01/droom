package com.example.utils

import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSerialization() {

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
            setDateFormat("dd.MM.yyyy HH:mm:ss.sss")
        }
    }
}
