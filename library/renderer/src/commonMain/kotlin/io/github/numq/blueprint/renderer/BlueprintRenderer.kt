package io.github.numq.blueprint.renderer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import io.github.numq.blueprint.renderer.local.LocalBlueprintState
import io.github.numq.blueprint.renderer.local.LocalErrorHandler
import io.github.numq.blueprint.renderer.local.LocalIntentHandler
import io.github.numq.blueprint.runtime.Blueprint
import io.github.numq.blueprint.runtime.BlueprintNode

interface BlueprintRenderer {
    @Composable
    fun render(node: BlueprintNode)

    @Composable
    fun render(blueprint: Blueprint, intentHandler: IntentHandler, errorHandler: ErrorHandler? = null) {
        CompositionLocalProvider(
            LocalBlueprintState provides blueprint.state,
            LocalIntentHandler provides intentHandler,
            LocalErrorHandler provides (errorHandler ?: LocalErrorHandler.current)
        ) {
            render(node = blueprint.root)
        }
    }
}