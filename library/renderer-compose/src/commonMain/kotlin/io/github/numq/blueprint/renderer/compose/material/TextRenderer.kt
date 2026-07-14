package io.github.numq.blueprint.renderer.compose.material

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.compose.toComposeModifier
import io.github.numq.blueprint.renderer.local.LocalBlueprintState
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.MaterialPayload
import io.github.numq.blueprint.runtime.type.resolve

object TextRenderer : ComponentRenderer<MaterialPayload.Text> {
    @Composable
    override fun render(
        node: BlueprintNode, payload: MaterialPayload.Text, renderer: BlueprintRenderer
    ) {
        val state = LocalBlueprintState.current

        Text(
            text = payload.content.resolve(state),
            style = payload.size.toComposeTextStyle(),
            color = payload.colorHex?.let {
                val cleanHex = it.removePrefix("#")

                val colorLong = cleanHex.toLong(16)

                if (cleanHex.length == 6) Color(colorLong or 0xFF000000) else Color(colorLong)
            } ?: payload.color.toComposeColor(),
            textAlign = payload.align.toComposeTextAlign(),
            maxLines = payload.maxLines,
            modifier = node.modifiers.toComposeModifier())
    }
}