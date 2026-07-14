package io.github.numq.blueprint.renderer.compose.layout

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.compose.toComposeModifier
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.LayoutPayload

object SpacerRenderer : ComponentRenderer<LayoutPayload.Spacer> {
    @Composable
    override fun render(node: BlueprintNode, payload: LayoutPayload.Spacer, renderer: BlueprintRenderer) {
        Spacer(modifier = node.modifiers.toComposeModifier().size(payload.size.dp))
    }
}