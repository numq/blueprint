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
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf

class ApplicationStore(private val scope: CoroutineScope, private val client: HttpClient) {
    @OptIn(ExperimentalSerializationApi::class)
    private val protoBuf = ProtoBuf

    private val _state = MutableStateFlow(ApplicationState())

    val state = _state.asStateFlow()

    private fun handleResolution(resolution: Resolution) {
        _state.update { currentState ->
            var newChain = currentState.chain

            resolution.effects.forEach { effect ->
                when (effect) {
                    is Effect.Navigation -> {
                        if (effect.type == Effect.Navigation.Type.POP) {
                            newChain = newChain.pop()
                        } else if (effect.blueprint != null) {
                            newChain = if (effect.type == Effect.Navigation.Type.REPLACE) {
                                newChain.replace(effect.blueprint!!)
                            } else {
                                newChain.push(effect.blueprint!!)
                            }
                        }
                    }

                    is Effect.Snackbar -> println("Show Snackbar: ${effect.message} (Error: ${effect.isError})")

                    is Effect.Dialog -> println("Show Dialog: ${effect.title} - ${effect.message}")
                }
            }

            newChain = newChain.applyDeltaBlocks(resolution.deltaBlocks)

            currentState.copy(chain = newChain, isLoading = false)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun dispatch(intent: Intent) {
        scope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val intentBytes = protoBuf.encodeToByteArray(Intent.serializer(), intent)

                val response = client.post("http://localhost:8080/action") {
                    contentType(ContentType.Application.ProtoBuf)
                    setBody(intentBytes)
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