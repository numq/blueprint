package io.github.numq.blueprint.renderer

import androidx.compose.runtime.Composable
import io.github.numq.blueprint.runtime.BlueprintNode

internal class DefaultErrorHandler : ErrorHandler {
    override fun onError(error: Throwable, node: BlueprintNode) {
        println("Blueprint Render Error in node [${node.key}]: ${error.message}")
    }

    @Composable
    override fun renderError(node: BlueprintNode, error: Throwable) = Unit
}