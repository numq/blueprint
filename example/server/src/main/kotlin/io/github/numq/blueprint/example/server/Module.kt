package io.github.numq.blueprint.example.server

import io.github.numq.blueprint.runtime.action.Effect
import io.github.numq.blueprint.runtime.action.Intent
import io.github.numq.blueprint.runtime.action.Resolution
import io.ktor.http.*
import io.ktor.serialization.kotlinx.protobuf.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf

@OptIn(ExperimentalSerializationApi::class)
fun Application.module() {
    install(ContentNegotiation) {
        protobuf(ProtoBuf)
    }

    val orderListScreen = OrderListScreen()

    val orderDetailScreen = OrderDetailScreen()

    val trackingScreen = TrackingScreen()

    routing {
        post("/action") {
            // session header Authorization (JWT)
            val sessionId = call.request.headers["Session-Id"] ?: "anonymous_user_123"

            val intent = call.receive<Intent>()
            println("Server received Intent: ${intent.id} of type ${intent.type}")

            val currentHash = SessionManager.getCurrentHash(sessionId)

            val resolution = when {
                intent.id == "start" -> {
                    SessionManager.clearSession(sessionId)

                    val blueprint = CryptoUtils.createSignedBlueprint(orderListScreen.create(), null)

                    SessionManager.pushHash(sessionId, blueprint.hash)

                    Resolution(
                        effects = listOf(
                            Effect.Navigation(
                                blueprint = blueprint, type = Effect.Navigation.Type.REPLACE
                            )
                        )
                    )
                }

                intent.id.startsWith("navigate_order_detail:") -> {
                    val id = intent.id.removePrefix("navigate_order_detail:")

                    val blueprint = CryptoUtils.createSignedBlueprint(orderDetailScreen.create(id), currentHash)

                    SessionManager.pushHash(sessionId, blueprint.hash)

                    Resolution(effects = listOf(Effect.Navigation(blueprint = blueprint)))
                }

                intent.id.startsWith("navigate_tracking:") -> {
                    val id = intent.id.removePrefix("navigate_tracking:")

                    val blueprint = CryptoUtils.createSignedBlueprint(trackingScreen.create(id), currentHash)

                    SessionManager.pushHash(sessionId, blueprint.hash)
                    Resolution(effects = listOf(Effect.Navigation(blueprint = blueprint)))
                }

                intent.id == "navigate_back" -> {
                    SessionManager.popHash(sessionId)

                    Resolution(effects = listOf(Effect.Navigation(type = Effect.Navigation.Type.POP)))
                }

                intent.id.startsWith("contact_support:") -> {
                    if (currentHash != null) {
                        val patches = mapOf("support_status" to "connecting")

                        val deltaBlock = CryptoUtils.createDeltaBlock(currentHash, patches)

                        SessionManager.popHash(sessionId)

                        SessionManager.pushHash(sessionId, deltaBlock.newHash)

                        Resolution(
                            deltaBlocks = listOf(deltaBlock),
                            effects = listOf(Effect.Snackbar(message = "Connecting you to support..."))
                        )
                    } else Resolution()
                }

                else -> Resolution()
            }

            try {
                call.respond(resolution)
            } catch (e: Exception) {
                e.printStackTrace()

                call.respondText(
                    text = "Resolution serialization error: ${e.message}", status = HttpStatusCode.InternalServerError
                )
            }
        }
    }
}