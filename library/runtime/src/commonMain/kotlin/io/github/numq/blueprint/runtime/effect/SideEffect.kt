package io.github.numq.blueprint.runtime.effect

sealed interface SideEffect {
    data class ShowSnackbar(val message: String, val isError: Boolean) : SideEffect

    data class ShowDialog(val title: String, val message: String) : SideEffect
}