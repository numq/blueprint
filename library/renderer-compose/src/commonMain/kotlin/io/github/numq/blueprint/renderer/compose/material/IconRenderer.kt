package io.github.numq.blueprint.renderer.compose.material

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.compose.toComposeModifier
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.MaterialPayload

object IconRenderer : ComponentRenderer<MaterialPayload.Icon> {
    @Composable
    override fun render(node: BlueprintNode, payload: MaterialPayload.Icon, renderer: BlueprintRenderer) {
        // todo map String to ImageVector or Painter
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = payload.name,
            tint = payload.tint.toComposeColor(),
            modifier = node.modifiers.toComposeModifier()
        )
    }
}