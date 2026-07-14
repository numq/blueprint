package io.github.numq.blueprint.example.client

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.numq.blueprint.renderer.IntentHandler
import io.github.numq.blueprint.renderer.compose.createDefaultBlueprintRegistry
import io.github.numq.blueprint.runtime.Blueprint
import io.github.numq.blueprint.runtime.action.Effect
import io.github.numq.blueprint.runtime.action.Intent
import io.github.numq.blueprint.runtime.action.Resolution
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.launch

@Composable
fun Application(client: HttpClient) {
    val backStack = remember { mutableStateListOf<Screen>(Screen.OrderList) }

    val currentScreen = backStack.lastOrNull() ?: Screen.OrderList

    var blueprint by remember { mutableStateOf<Blueprint?>(null) }

    var error by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    fun loadScreen(url: String) {
        scope.launch {
            isLoading = true

            error = null

            try {
                val response = client.get("http://localhost:8080$url")

                if (response.status.isSuccess()) {
                    blueprint = response.body<Blueprint>()
                } else {
                    error = "Server Error ${response.status.value}: ${response.bodyAsText()}"
                }
            } catch (e: Exception) {
                e.printStackTrace()

                error = e.message
            } finally {
                isLoading = false
            }
        }
    }

    val intentHandler = remember {
        IntentHandler { intent: Intent ->
            scope.launch {
                try {
                    val response = client.post("http://localhost:8080/action") {
                        contentType(ContentType.Application.Json)
                        setBody(intent)
                    }

                    if (response.status.isSuccess()) {
                        val resolution = response.body<Resolution>()

                        if (resolution.statePatches.isNotEmpty()) {
                            blueprint = blueprint?.let { currentBlueprint ->
                                currentBlueprint.copy(state = currentBlueprint.state + resolution.statePatches)
                            }
                        }

                        resolution.effects.forEach { effect ->
                            when (effect) {
                                is Effect.Navigation -> {
                                    if (effect.screenId == "back" || effect.type == Effect.Navigation.Type.POP) {
                                        if (backStack.size > 1) {
                                            backStack.removeLast()
                                        }
                                    } else {
                                        val nextScreen = when (effect.screenId) {
                                            "order_list" -> Screen.OrderList

                                            "order_detail" -> {
                                                val id = effect.params["id"] ?: return@forEach

                                                Screen.OrderDetail(id)
                                            }

                                            "tracking" -> {
                                                val id = effect.params["id"] ?: return@forEach

                                                Screen.Tracking(id)
                                            }

                                            else -> null
                                        }

                                        if (nextScreen != null) {
                                            if (effect.type == Effect.Navigation.Type.REPLACE) {
                                                backStack.removeLast()
                                            }

                                            if (backStack.lastOrNull() != nextScreen) {
                                                backStack.add(nextScreen)
                                            }
                                        }
                                    }
                                }

                                is Effect.Snackbar -> println("Show Snackbar: ${effect.message} (Error: ${effect.isError})")

                                is Effect.Dialog -> println("Show Dialog: ${effect.title} - ${effect.message}")
                            }
                        }
                    } else {
                        val errorText = response.bodyAsText()

                        error = "Action Error ${response.status.value}: $errorText"

                        println("Server action error: $errorText")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()

                    error = "Network error: ${e.message}"
                }
            }
        }
    }

    val renderer = remember { createDefaultBlueprintRegistry() }

    LaunchedEffect(currentScreen) {
        when (currentScreen) {
            is Screen.OrderList -> loadScreen("/orders")

            is Screen.OrderDetail -> loadScreen("/orders/${currentScreen.orderId}")

            is Screen.Tracking -> loadScreen("/orders/${currentScreen.orderId}/tracking")
        }
    }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
            when {
                isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

                error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
                }

                else -> blueprint?.let { blueprint ->
                    renderer.render(blueprint = blueprint, intentHandler = intentHandler)
                }
            }
        }
    }
}