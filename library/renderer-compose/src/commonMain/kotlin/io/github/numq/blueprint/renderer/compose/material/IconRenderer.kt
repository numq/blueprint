package io.github.numq.blueprint.renderer.compose.material

import androidx.compose.runtime.Composable
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.compose.toComposeModifier
import io.github.numq.blueprint.renderer.local.LocalIconProvider
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.MaterialPayload

object IconRenderer : ComponentRenderer<MaterialPayload.Icon> {
    @Composable
    override fun render(node: BlueprintNode, payload: MaterialPayload.Icon, renderer: BlueprintRenderer) {
        val iconProvider = LocalIconProvider.current

        iconProvider(payload.name, payload.tint.toComposeColor(), payload.size, node.modifiers.toComposeModifier())
    }
}