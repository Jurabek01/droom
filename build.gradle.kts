val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "io.mimsoft"
version = "0.0.1"
application {
    mainClass.set("io.mimsoft.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation( "io.ktor:ktor-server-locations-jvm:$ktor_version")
    implementation( "io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation( "io.ktor:ktor-serialization-gson-jvm:$ktor_version")
    implementation( "io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation( "io.ktor:ktor-server-cors-jvm:$ktor_version")
    implementation( "io.ktor:ktor-server-auth-jwt:$ktor_version")
    implementation( "io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation( "io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation( "io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation( "io.ktor:ktor-server-status-pages-jvm:$ktor_version")
    implementation( "ch.qos.logback:logback-classic:$logback_version")
//    implementation ("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    testImplementation ("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation ("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("org.postgresql:postgresql:42.3.6")
    implementation("com.github.jasync-sql:jasync-postgresql:2.0.8")
}