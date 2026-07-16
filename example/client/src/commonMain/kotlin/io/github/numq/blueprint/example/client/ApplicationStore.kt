package io.github.numq.blueprint.example.client

import io.github.numq.blueprint.runtime.action.Effect
import io.github.numq.blueprint.runtime.action.Intent
import io.github.numq.blueprint.runtime.action.Resolution
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ApplicationStore(private val scope: CoroutineScope, private val client: HttpClient) {
    private val _state = MutableStateFlow(ApplicationState())

    val state = _state.asStateFlow()

    private fun handleResolution(resolution: Resolution) {
        _state.update { currentState ->
            val newStack = currentState.backStack.toMutableList()

            resolution.effects.forEach { effect ->
                when (effect) {
                    is Effect.Navigation -> {
                        val blueprint = effect.blueprint

                        if (effect.type == Effect.Navigation.Type.POP) {
                            if (newStack.size > 1) {
                                newStack.removeLast()
                            }
                        } else if (blueprint != null) {
                            if (effect.type == Effect.Navigation.Type.REPLACE && newStack.isNotEmpty()) {
                                newStack.removeLast()
                            }
                            newStack.add(blueprint)
                        }
                    }

                    is Effect.Snackbar -> println("Show Snackbar: ${effect.message} (Error: ${effect.isError})")

                    is Effect.Dialog -> println("Show Dialog: ${effect.title} - ${effect.message}")
                }
            }

            if (resolution.statePatches.isNotEmpty() && newStack.isNotEmpty()) {
                val topIndex = newStack.lastIndex

                val topBlueprint = newStack[topIndex]

                newStack[topIndex] = topBlueprint.copy(state = topBlueprint.state + resolution.statePatches)
            }

            currentState.copy(backStack = newStack, isLoading = false)
        }
    }

    fun dispatch(intent: Intent) {
        scope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val response = client.post("http://localhost:8080/action") {
                    contentType(ContentType.Application.Json)
                    setBody(intent)
                }

                if (response.status.isSuccess()) {
                    val resolution = response.body<Resolution>()

                    handleResolution(resolution)
                } else {
                    val errorText = response.bodyAsText()

                    _state.update {
                        it.copy(error = "Action Error ${response.status.value}: $errorText", isLoading = false)
                    }

                    println("Server action error: $errorText")
                }
            } catch (e: Exception) {
                e.printStackTrace()

                _state.update {
                    it.copy(error = "Network error: ${e.message}", isLoading = false)
                }
            }
        }
    }
}