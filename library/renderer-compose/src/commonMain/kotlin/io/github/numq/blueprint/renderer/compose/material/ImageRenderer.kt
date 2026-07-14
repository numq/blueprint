package io.github.numq.blueprint.renderer.compose.material

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.compose.toComposeModifier
import io.github.numq.blueprint.renderer.local.LocalBlueprintState
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.MaterialPayload
import io.github.numq.blueprint.runtime.type.resolve

object ImageRenderer : ComponentRenderer<MaterialPayload.Image> {
    @Composable
    override fun render(node: BlueprintNode, payload: MaterialPayload.Image, renderer: BlueprintRenderer) {
        val state = LocalBlueprintState.current

        val url = payload.url.resolve(state)

        // todo Coil
        Box(modifier = node.modifiers.toComposeModifier(), contentAlignment = Alignment.Center) {
            Text(text = "Image [$url]")
        }
    }
}