package io.github.numq.blueprint.renderer.compose.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.compose.extractWeight
import io.github.numq.blueprint.renderer.compose.toComposeModifier
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.LayoutPayload

object ColumnRenderer : ComponentRenderer<LayoutPayload.Column> {
    @Composable
    override fun render(node: BlueprintNode, payload: LayoutPayload.Column, renderer: BlueprintRenderer) {
        Column(
            modifier = node.modifiers.toComposeModifier(),
            verticalArrangement = payload.verticalArrangement.toVertical(),
            horizontalAlignment = payload.horizontalAlignment.toHorizontal()
        ) {
            node.children.forEachIndexed { index, child ->
                val weight = child.modifiers.extractWeight

                Box(modifier = if (weight > 0f) Modifier.weight(weight) else Modifier) {
                    renderer.render(child)
                }

                if (index < node.children.lastIndex) {
                    Spacer(modifier = Modifier.height(payload.spacing.dp))
                }
            }
        }
    }
}