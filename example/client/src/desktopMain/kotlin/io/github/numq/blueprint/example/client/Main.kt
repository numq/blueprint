package io.github.numq.blueprint.example.client

import androidx.compose.runtime.remember
import androidx.compose.ui.window.singleWindowApplication
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.protobuf.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf

@OptIn(ExperimentalSerializationApi::class)
fun main() = singleWindowApplication {
    val client = remember {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                protobuf(ProtoBuf)
            }
        }
    }

    Application(client = client)
}