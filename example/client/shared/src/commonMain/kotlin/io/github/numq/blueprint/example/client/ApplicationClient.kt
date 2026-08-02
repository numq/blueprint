package io.github.numq.blueprint.example.client

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.protobuf.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf

@OptIn(ExperimentalSerializationApi::class)
private fun createApplicationClient() = HttpClient {
    install(ContentNegotiation) {
        protobuf(ProtoBuf {
            serializersModule = applicationSerializersModule
        })
    }
}

@Composable
fun rememberApplicationClient(): HttpClient {
    val client = remember { createApplicationClient() }

    DisposableEffect(client) {
        onDispose(client::close)
    }

    return client
}