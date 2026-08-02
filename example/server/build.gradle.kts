plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    application
}

group = "io.github.numq.blueprint.example"
version = "1.0.0"

application {
    mainClass = "io.github.numq.blueprint.example.ApplicationKt"
}

dependencies {
    implementation(projects.library.runtime)
    implementation(projects.library.dsl)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.protobuf)
    implementation(libs.wire.runtime)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.serialization.kotlinx.protobuf)
}