package io.github.numq.blueprint.renderer

import androidx.compose.runtime.Composable
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.ComponentPayload

interface ComponentRenderer<T : ComponentPayload> {
    @Composable
    fun render(node: BlueprintNode, payload: T, renderer: BlueprintRenderer)
}