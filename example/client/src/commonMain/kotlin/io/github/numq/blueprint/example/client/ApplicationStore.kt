package io.github.numq.blueprint.example.client

import io.github.numq.blueprint.chain.ChainError
import io.github.numq.blueprint.chain.ChainEvent
import io.github.numq.blueprint.runtime.action.Effect
import io.github.numq.blueprint.runtime.action.Intent
import io.github.numq.blueprint.runtime.action.Resolution
import io.github.numq.blueprint.runtime.effect.SideEffect
import io.github.numq.blueprint.runtime.fp.Either
import io.github.numq.blueprint.runtime.fp.foldEither
import io.github.numq.blueprint.runtime.fp.left
import io.github.numq.blueprint.runtime.fp.right
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

    private fun updateState(resolution: Resolution) {
        val events = buildList {
            resolution.effects.forEach { effect ->
                if (effect is Effect.Navigation) {
                    when (effect.type) {
                        Effect.Navigation.Type.PUSH -> effect.blueprint?.let { blueprint ->
                            add(ChainEvent.Push(blueprint = blueprint))
                        }

                        Effect.Navigation.Type.POP -> add(ChainEvent.Pop)

                        Effect.Navigation.Type.REPLACE -> effect.blueprint?.let { blueprint ->
                            add(ChainEvent.Replace(blueprint = blueprint))
                        }
                    }
                }
            }

            if (resolution.deltaBlocks.isNotEmpty()) {
                add(ChainEvent.ApplyDeltas(blocks = resolution.deltaBlocks))
            }
        }

        _state.update { currentState ->
            when (val result =
                events.foldEither(initial = currentState.chain) { chain, event -> chain.reduce(event) }) {
                is Either.Left -> {
                    val errorMessage = when (val error = result.value) {
                        is ChainError.HashMismatch -> "Security Alert: Hash Mismatch (Expected ${error.expected})"

                        is ChainError.SignatureVerificationFailed -> "Security Alert: Invalid Server Signature"

                        is ChainError.CannotPopEmptyChain -> "Navigation Error: Cannot go back further"
                    }
                    currentState.copy(error = errorMessage, isLoading = false)
                }

                is Either.Right -> currentState.copy(chain = result.value, isLoading = false)
            }
        }
    }

    private fun extractSideEffects(resolution: Resolution) = buildList {
        resolution.effects.forEach { effect ->
            when (effect) {
                is Effect.Snackbar -> add(SideEffect.ShowSnackbar(effect.message, effect.isError))

                is Effect.Dialog -> add(SideEffect.ShowDialog(effect.title, effect.message))

                is Effect.Navigation -> Unit
            }
        }
    }

    private fun interpret(effect: SideEffect) {
        when (effect) {
            is SideEffect.ShowSnackbar -> println("Show Snackbar: ${effect.message} (Error: ${effect.isError})")

            is SideEffect.ShowDialog -> println("Show Dialog: ${effect.title} - ${effect.message}")
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun executeNetworkCall(intent: Intent): Either<String, Resolution> = try {
        val intentBytes = protoBuf.encodeToByteArray(Intent.serializer(), intent)

        val response = client.post("http://localhost:8080/action") {
            contentType(ContentType.Application.ProtoBuf)
            setBody(intentBytes)
        }

        if (response.status.isSuccess()) {
            response.body<Resolution>().right()
        } else {
            "Action Error ${response.status.value}: ${response.bodyAsText()}".left()
        }
    } catch (e: Exception) {
        "Network error: ${e.message}".left()
    }

    fun dispatch(intent: Intent) {
        scope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (val result = executeNetworkCall(intent)) {
                is Either.Left -> {
                    _state.update { it.copy(error = result.value, isLoading = false) }

                    println("Server/Network action error: ${result.value}")
                }

                is Either.Right -> {
                    val resolution = result.value

                    updateState(resolution)

                    extractSideEffects(resolution).forEach { effect -> interpret(effect) }
                }
            }
        }
    }
}