package io.github.numq.blueprint.renderer.compose.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.compose.toComposeModifier
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.LayoutPayload

object BoxRenderer : ComponentRenderer<LayoutPayload.Box> {
    @Composable
    override fun render(node: BlueprintNode, payload: LayoutPayload.Box, renderer: BlueprintRenderer) {
        Box(
            modifier = node.modifiers.toComposeModifier(), contentAlignment = payload.contentAlignment.toBoxAlignment()
        ) {
            node.children.forEach { child ->
                renderer.render(child)
            }
        }
    }
}