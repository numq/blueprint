package io.github.numq.blueprint.example.server

import io.github.numq.blueprint.runtime.action.Effect
import io.github.numq.blueprint.runtime.action.Intent
import io.github.numq.blueprint.runtime.action.Resolution
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        })
    }

    val orderListScreen = OrderListScreen()

    val orderDetailScreen = OrderDetailScreen()

    val trackingScreen = TrackingScreen()

    routing {
        get("/orders") {
            call.respond(orderListScreen.create())
        }

        get("/orders/{id}") {
            val orderId = call.parameters["id"] ?: ""

            call.respond(orderDetailScreen.create(orderId))
        }

        get("/orders/{id}/tracking") {
            val orderId = call.parameters["id"] ?: ""

            call.respond(trackingScreen.create(orderId))
        }

        post("/action") {
            val intent = call.receive<Intent>()

            println("Server received Intent: ${intent.id} of type ${intent.type}")

            val resolution = when {
                intent.id.startsWith("navigate_order_detail:") -> {
                    val id = intent.id.removePrefix("navigate_order_detail:")

                    Resolution(
                        effects = listOf(
                            Effect.Navigation(
                                screenId = "order_detail", params = mapOf("id" to id)
                            )
                        )
                    )
                }

                intent.id.startsWith("navigate_tracking:") -> {
                    val id = intent.id.removePrefix("navigate_tracking:")

                    Resolution(effects = listOf(Effect.Navigation(screenId = "tracking", params = mapOf("id" to id))))
                }

                intent.id == "navigate_back" -> Resolution(effects = listOf(Effect.Navigation(screenId = "back")))

                intent.id.startsWith("contact_support:") -> Resolution(effects = listOf(Effect.Snackbar(message = "Connecting you to support...")))

                else -> Resolution()
            }

            try {
                val myJson = Json {
                    encodeDefaults = true
                }

                val jsonString = myJson.encodeToString(resolution)

                call.respondText(text = jsonString, contentType = ContentType.Application.Json)
            } catch (e: Exception) {
                e.printStackTrace()

                call.respondText(
                    text = "Resolution serialization error: ${e.message}", status = HttpStatusCode.InternalServerError
                )
            }
        }
    }
}