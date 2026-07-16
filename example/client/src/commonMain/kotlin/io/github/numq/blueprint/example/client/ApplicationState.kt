package io.github.numq.blueprint.example.client

import io.github.numq.blueprint.runtime.Blueprint

data class ApplicationState(
    val backStack: List<Blueprint> = emptyList(), val isLoading: Boolean = false, val error: String? = null
) {
    val currentBlueprint: Blueprint? get() = backStack.lastOrNull()
}