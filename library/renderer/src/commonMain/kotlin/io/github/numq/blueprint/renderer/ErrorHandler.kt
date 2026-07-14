package io.github.numq.blueprint.renderer

import androidx.compose.runtime.Composable
import io.github.numq.blueprint.runtime.BlueprintNode

interface ErrorHandler {
    @Composable
    fun renderError(node: BlueprintNode, error: Throwable)

    fun onError(error: Throwable, node: BlueprintNode)
}