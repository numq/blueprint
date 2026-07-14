package io.github.numq.blueprint.renderer

import io.github.numq.blueprint.runtime.BlueprintNode

internal class DefaultErrorHandler : ErrorHandler {
    override fun onError(error: Throwable, node: BlueprintNode) {
        println("Blueprint Render Error in node [${node.key}]: ${error.message}")
    }

    @androidx.compose.runtime.Composable
    override fun renderError(node: BlueprintNode, error: Throwable) = Unit
}