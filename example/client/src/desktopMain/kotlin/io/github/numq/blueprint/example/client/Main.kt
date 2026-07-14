package io.github.numq.blueprint.example.client

import androidx.compose.runtime.remember
import androidx.compose.ui.window.singleWindowApplication
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun main() = singleWindowApplication {
    val client = remember {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
        }
    }

    Application(client = client)
}